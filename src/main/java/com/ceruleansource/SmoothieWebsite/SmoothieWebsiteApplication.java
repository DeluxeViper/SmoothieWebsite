package com.ceruleansource.SmoothieWebsite;

import com.ceruleansource.SmoothieWebsite.backend.Models.User;
import com.ceruleansource.SmoothieWebsite.backend.Repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class SmoothieWebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmoothieWebsiteApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(UserRepository repository) {
        return (args -> {
//            repository.save(new User("Bruce Lee", "hello@gmail.com", "pasword", new ArrayList<>(), "0"));
        });
    }

}
