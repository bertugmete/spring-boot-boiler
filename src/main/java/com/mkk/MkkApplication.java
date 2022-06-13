package com.mkk;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class MkkApplication {

	public static void main(String[] args) {
		SpringApplication.run(MkkApplication.class, args);
	}

}
