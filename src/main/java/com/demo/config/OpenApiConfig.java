package com.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;


@Configuration
@OpenAPIDefinition( 
		info = @Info( title = "Service API", version = "v1",
		description = "서비스의 REST API 명세" ), servers = {@Server(url = "/", description = "기본 서버 환경") } 
)
public class OpenApiConfig {
	
	/*
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Open Board API 문서")
                        .description("커뮤니티 게시판 서비스 API")
                        .version("1.0"))
                .addSecurityItem(new SecurityRequirement())
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")));
    }
	 */                 
	
}