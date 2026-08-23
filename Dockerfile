FROM eclipse-temurin:17-jdk

# 빌드된 jar 파일 경로 설정 (Gradle 기본 빌드 폴더 기준)
ARG JAR_FILE=build/libs/*.jar

# jar 파일을 도커 컨테이너 내부의 app.jar로 복사
COPY ${JAR_FILE} app.jar

# 컨테이너가 외부로 노출할 포트
EXPOSE 8080

# 컨테이너 실행 시 실행될 명령어
ENTRYPOINT ["java", "-jar", "/app.jar"]