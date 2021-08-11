package com.eb1;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class ElectronicBazar1Application {

	public static void main(String[] args) {
		SpringApplication.run(ElectronicBazar1Application.class, args);
	}

	
	protected SpringApplicationBuilder configure (SpringApplicationBuilder application) {
		return application.sources(ElectronicBazar1Application.class);
	}


}

