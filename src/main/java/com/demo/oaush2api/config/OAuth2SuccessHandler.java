package com.demo.oaush2api.config;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.demo.oaush2api.dto.User;
import com.demo.oaush2api.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {

        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        String registrationId = authToken.getAuthorizedClientRegistrationId(); // google, kakao, naver

        OAuth2User oAuth2User = authToken.getPrincipal();
        OAuth2UserInfo userInfo = OAuth2UserInfo.of(registrationId, oAuth2User.getAttributes());

        // DB에서 가입된 User 정보 조회
        Optional<User> userOptional = userRepository.findByEmail(userInfo.getEmail());

        
        if (userOptional.isEmpty()) {
            // [회원 정보 없음] -> index.do 페이지로 이동시키며 미가입 상태 전달 (Confirm 팝업용)
            String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:8080/login/index.do")
                    .queryParam("registered", "false")
                    .queryParam("email", userInfo.getEmail())
                    .queryParam("name", userInfo.getName() != null ? userInfo.getName() : "")
                    .queryParam("provider", userInfo.getProvider())
                    .queryParam("providerId", userInfo.getProviderId())
                    .build()
                    .encode(StandardCharsets.UTF_8) // 여기서 일괄 인코딩 처리
                    .toUriString();

            getRedirectStrategy().sendRedirect(request, response, targetUrl);
        } else {
            // [회원 정보 있음] -> 로그인 성공 처리 및 JWT 발급
            User user = userOptional.get();
            String jwtToken = jwtProvider.createToken(user.getUserId(), user.getEmail(), user.getRole());

            String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:8080/login/callback")
                    .queryParam("token", jwtToken)
                    .build().toUriString();

            getRedirectStrategy().sendRedirect(request, response, targetUrl);
        }
 
        /*
        // JWT Access Token 생성
        String jwtToken = jwtProvider.createToken(user.getUserId(), user.getEmail(), user.getRole());

        // REST API 응답 설정 (JSON 형태)
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(String.format(
                "{\"message\": \"로그인 성공\", \"accessToken\": \"%s\", \"userId\": %d, \"email\": \"%s\", \"role\": \"%s\"}",
                jwtToken, user.getUserId(), user.getEmail(), user.getRole().name()
        ));
        */
    }
    
}