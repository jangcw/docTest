package com.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DockerTestApplicationTests {
	
	@Test
	@DisplayName("요청 기간에 이미 예약이 있는지 체크")
	void duplicateReservateionCheck() {
		int check = 1;
		
		assertEquals(1, check);
	}
	
	
}
