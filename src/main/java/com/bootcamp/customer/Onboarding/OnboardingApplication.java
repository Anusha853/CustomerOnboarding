package com.bootcamp.customer.Onboarding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.SpringVersion;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })

public class OnboardingApplication {

	public static void main(String[] args) {

		SpringApplication.run(OnboardingApplication.class, args);

	}

}
