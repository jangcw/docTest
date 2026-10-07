package com.demo.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("사용자 관리")
                //.pathsToMatch("/users/**") // controller requestMapping의 경로를 말한다.
                .packagesToScan("com.demo.controller")
                .build();
    }

    @Bean
    public GroupedOpenApi mcpApi() {
        return GroupedOpenApi.builder()
                .group("mcp 접속")
                //.pathsToMatch("/menus/**")
                .packagesToScan("com.demo.controller") // 다르게 해야 분리됨
                .build();
    }

    @Bean
    public GroupedOpenApi kafkaMessageApi() {
        return GroupedOpenApi.builder()
                .group("kafka 메시지 처리")
                //.pathsToMatch("/menus/**")
                .packagesToScan("com.demo.controller")
                .build();
    }
 
}