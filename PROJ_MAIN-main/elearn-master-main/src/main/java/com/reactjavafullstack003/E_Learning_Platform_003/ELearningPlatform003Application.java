package com.reactjavafullstack003.E_Learning_Platform_003;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication
public class ELearningPlatform003Application {

	public static void main(String[] args) {
		SpringApplication.run(ELearningPlatform003Application.class, args);
	}

	//Whole Application is running from this class
	//This class is the entry point of the application

}
