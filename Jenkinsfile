pipeline {

    agent any

    environment {
        // Jenkins Build Number
        DOCKER_IMAGE = 'spring-app'
        DOCKER_TAG = "${BUILD_NUMBER}"
    }

    stages {

        /*
         * 1. GitHub 소스 가져오기
         */
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        /*
         * 2. Spring Boot 테스트 및 빌드
         */
        stage('Build & Test') {
            steps {
                sh '''
                    chmod +x gradlew
                    ./gradlew clean test bootJar
                '''
            }
        }

        /*
         * 3. Docker Image 생성
         */
        stage('Docker Build') {

            steps {

                sh """
                    docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} .
                    docker tag ${DOCKER_IMAGE}:${DOCKER_TAG} ${DOCKER_IMAGE}:latest
                """

            }
        }


        /*
         * 4. Docker Hub 로그인 및 Push
         */
        stage('Docker Push') {

            steps {

                withCredentials([
					// jenkins credential에 설정된 username, password가 실행 시 자동 매핑된다. 변수명이다
                    usernamePassword(
                        credentialsId: 'docker-hub-credential',
                        usernameVariable: 'DOCKER_USERNAME',
                        passwordVariable: 'DOCKER_PASSWORD'
                    )
                ]) {

                    sh '''
                        echo "$DOCKER_PASSWORD" | \
                        docker login \
                        -u "$DOCKER_USERNAME" \
                        --password-stdin

                        docker push ${DOCKER_IMAGE}:${DOCKER_TAG}
                        docker push ${DOCKER_IMAGE}:latest
                        
                        docker logout
                    '''
                }
            }
        }


        /*
         * 5. Kubernetes Deployment
         */
        stage('Deploy Kubernetes') {

            steps {

                sh """
                    kubectl apply -f kubernetes/spring-deployment.yaml

                    kubectl apply -f kubernetes/spring-service.yaml

                    kubectl set image deployment/spring-app \
                        spring-app=${DOCKER_IMAGE}:${DOCKER_TAG}

                    kubectl rollout status deployment/spring-app
                """

            }
        }
    }


    /*
     * Pipeline 결과
     */
    post {

        success {

            echo '''
            ========================================
             Deployment SUCCESS
            ========================================
            '''

            echo "Docker Image : ${DOCKER_IMAGE}:${DOCKER_TAG}"
        }

        failure {

            echo '''
            ========================================
             Deployment FAILED
            ========================================
            '''
        }
    }
}
