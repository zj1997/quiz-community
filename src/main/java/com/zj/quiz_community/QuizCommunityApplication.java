package com.zj.quiz_community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:velocity.xml"})
public class QuizCommunityApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizCommunityApplication.class, args);
	}
}
