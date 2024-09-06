package com.bootcamp.customer.Onboarding.config;

import com.mailjet.client.MailjetClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailjetConfig {

    private final String API_KEY = "05e197f568c58668f328fa4f6444e9ca";
    private final String SECRET_KEY = "0563d36b48630c7721402cc06b8762d3";

    @Bean
    public MailjetClient mailjetClient() {
        return new MailjetClient(API_KEY, SECRET_KEY);
    }
}