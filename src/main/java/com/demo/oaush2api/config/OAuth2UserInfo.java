package com.demo.oaush2api.config;

import java.util.Map;

public abstract class OAuth2UserInfo {
    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getProviderId();
    public abstract String getProvider();
    public abstract String getEmail();
    public abstract String getName();

    // Provider별 Attribute 매핑 팩토리 메서드
    public static OAuth2UserInfo of(String provider, Map<String, Object> attributes) {
        if ("google".equalsIgnoreCase(provider)) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if ("kakao".equalsIgnoreCase(provider)) {
            return new KakaoOAuth2UserInfo(attributes);
        } else if ("naver".equalsIgnoreCase(provider)) {
            return new NaverOAuth2UserInfo(attributes);
        }
        throw new IllegalArgumentException("지원하지 않는 OAuth2 Provider입니다: " + provider);
    }
}

// Google 구현체
class GoogleOAuth2UserInfo extends OAuth2UserInfo {
    public GoogleOAuth2UserInfo(Map<String, Object> attributes) { super(attributes); }
    @Override public String getProviderId() { return (String) attributes.get("sub"); }
    @Override public String getProvider() { return "google"; }
    @Override public String getEmail() { return (String) attributes.get("email"); }
    @Override public String getName() { return (String) attributes.get("name"); }
}

// Kakao 구현체
class KakaoOAuth2UserInfo extends OAuth2UserInfo {
    public KakaoOAuth2UserInfo(Map<String, Object> attributes) { super(attributes); }
    @Override public String getProviderId() { return String.valueOf(attributes.get("id")); }
    @Override public String getProvider() { return "kakao"; }
    @Override public String getEmail() {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        return account != null ? (String) account.get("email") : null;
    }
    @Override public String getName() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        return properties != null ? (String) properties.get("nickname") : null;
    }
}

// Naver 구현체
class NaverOAuth2UserInfo extends OAuth2UserInfo {
    private final Map<String, Object> response;

    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
        this.response = (Map<String, Object>) attributes.get("response");
    }
    @Override public String getProviderId() { return (String) response.get("id"); }
    @Override public String getProvider() { return "naver"; }
    @Override public String getEmail() { return (String) response.get("email"); }
    @Override public String getName() { return (String) response.get("name"); }
}