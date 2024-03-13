package com.security.exp.security;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
		System.out.println("\n\nAPPLICATION STARTED\n\n");
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

}
