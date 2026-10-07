package com.demo.oaush2api.service;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.oaush2api.config.OAuth2UserInfo;
import com.demo.oaush2api.dto.Role;
import com.demo.oaush2api.dto.User;
import com.demo.oaush2api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        ClientRegistration registration =
                userRequest.getClientRegistration();

        System.out.println("registrationId = "
                + registration.getRegistrationId());

        System.out.println("userNameAttribute = "
                + registration.getProviderDetails()
                             .getUserInfoEndpoint()
                             .getUserNameAttributeName());
        
        // 1. 소셜 Provider(Google, Kakao, Naver)로부터 사용자 정보(Attributes) 조회
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 2. 어떤 Provider인지 확인 (google, kakao, naver)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // 3. Provider별로 서로 다른 Attribute 구조를 표준화
        OAuth2UserInfo userInfo = OAuth2UserInfo.of(registrationId, oAuth2User.getAttributes());

        // 4. DB에 회원가입 등록 또는 기존 회원 정보 업데이트 (로그인 처리)
        //User user = saveOrUpdate(userInfo);

        // 5. SecurityContext 및 SuccessHandler에서 참조할 수 있도록 OAuth2User 객체 반환
        return oAuth2User;
    }

    private User saveOrUpdate(OAuth2UserInfo userInfo) {
        // Email 기반으로 기존 가입 여부 확인 (소셜 고유 ID 기준도 가능)
        User user = userRepository.findByEmail(userInfo.getEmail())
                .map(entity -> entity.updateUserName(userInfo.getName())) // 기존 회원이면 이름 업데이트
                .orElseGet(() -> User.builder()                       // 신규 회원이 면 회원가입 진행
                        .email(userInfo.getEmail())
                        .userName(userInfo.getName()) 
                        .role(Role.USER) // 기본 권한: USER
                        .provider(userInfo.getProvider())
                        .providerId(userInfo.getProviderId())
                        .build());

        return userRepository.save(user);
    }
}