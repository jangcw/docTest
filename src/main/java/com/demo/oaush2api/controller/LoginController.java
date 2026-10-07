package com.demo.oaush2api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClient;

import com.demo.config.ApiMessage;
import com.demo.oaush2api.config.JwtAuthenticationService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {
	
	private final RestClient restClient;
	private final JwtAuthenticationService jwtAuthenticationService;
	
    // 1. 메인 / 로그인 페이지 (index.do)
    @GetMapping("/login/index.do")
    public String indexPage() {
        return "index";
    }

    // 2. 소셜 인증 완료 콜백 (JWT 수신 및 세션 저장)
    @GetMapping("/login/callback")
    public String loginCallback(@RequestParam("token") String token, HttpServletResponse response, HttpSession session) {
        session.setAttribute("JWT_TOKEN", token); // 발급받은 JWT 저장
        
        return "redirect:/users.do";
    }

    // 3. 회원가입 페이지 이동
    @GetMapping("/login/register.do")
    public String registerPage(
            @RequestParam("email") String email,
            @RequestParam("name") String name,
            @RequestParam("provider") String provider,
            @RequestParam("providerId") String providerId,
            Model model) {

        model.addAttribute("email", email);
        model.addAttribute("name", name);
        model.addAttribute("provider", provider);
        model.addAttribute("providerId", providerId);

        return "register";
    }

    // 4. 회원가입 REST API 요청 처리
    @PostMapping("/login/registerProc.do")
    public String processRegister(
    		@RequestParam("userId") String userId,
    		@RequestParam("passwd") String passwd,
    		@RequestParam("dept") String dept,
    		@RequestParam("mobile") String mobile,
            @RequestParam("email") String email,
            @RequestParam("userName") String userName,
            @RequestParam("provider") String provider,
            @RequestParam("providerId") String providerId) {

    	MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    	body.add("userId", userId);
    	body.add("passwd", passwd);
    	body.add("dept", dept);
    	body.add("mobile", mobile);
    	body.add("email", email);
    	body.add("userName", userName);
    	body.add("provider", provider);
    	body.add("providerId", providerId);

        restClient.put()
                .uri("/api/v1/auth/userSave")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(body)
                .retrieve()
                .toBodilessEntity();

        // 가입 완료 후 다시 로그인 페이지로 이동
        return "redirect:/login/index.do?registered=true";
    }

    // 5. 전체 사용자 리스트 페이지 (JWT 포함하여 REST API 호출)
    @GetMapping("/users.do")
    public String usersPage(HttpSession session, Model model) {
        String token = (String) session.getAttribute("JWT_TOKEN");
        if (token == null) {
            return "redirect:/login/index.do";
        }

        try {
        	ApiMessage<List<Map<String, Object>>> response = restClient.get()
                    .uri("/api/v1/user/userList")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});

            model.addAttribute("users", response.getData());
            return "users";
        } catch (Exception e) {
            return "redirect:/login/index.do";
        }
    }
}
