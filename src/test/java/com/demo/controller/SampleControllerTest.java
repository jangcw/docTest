package com.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SampleControllerTest {

	@LocalServerPort
    private int port;
	
	private RestClient restClient;
	
	@BeforeEach
	void setUp(@Value("${my.api.url}") String apiUrl) throws Exception {
	      restClient = RestClient.builder()
	                .baseUrl(apiUrl+":" + port)
	                .build();
	}
	
	@Test
	@DisplayName("Hello 테스트")
	void test() {
		String url = "/hello?userid="+"장철웅";
		
		//RestClient restClient = RestClient.create();

        ResponseEntity<String> response = restClient.get()
                .uri(url)
                .retrieve()
                .toEntity(String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

}
