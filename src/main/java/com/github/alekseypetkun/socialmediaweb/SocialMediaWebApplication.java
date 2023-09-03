package com.github.alekseypetkun.socialmediaweb;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
		title = "SocialMediaApplication"
))
public class SocialMediaWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialMediaWebApplication.class, args);
	}

}
