package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.UsersModel;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}
	
	//test for checkPassword() function
	@Test
	void checkPassowrdTest() {
		UsersModel user = new UsersModel();
		user.setPassword("123");
		user.setPasswordConfirm("123");
		assertEquals(true, user.checkPassword());
	}

}
