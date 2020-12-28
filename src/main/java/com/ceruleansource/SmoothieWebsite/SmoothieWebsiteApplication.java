package com.ceruleansource.SmoothieWebsite;

import com.ceruleansource.SmoothieWebsite.backend.Models.User;
import com.ceruleansource.SmoothieWebsite.backend.Repositories.IngredientRepository;
import com.ceruleansource.SmoothieWebsite.backend.Repositories.NutritionalInformationRepository;
import com.ceruleansource.SmoothieWebsite.backend.Repositories.SmoothieRepository;
import com.ceruleansource.SmoothieWebsite.backend.Repositories.UserRepository;
import com.ceruleansource.SmoothieWebsite.backend.Security.SecurityConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.ArrayList;

@SpringBootApplication
//@EnableJpaRepositories(basePackageClasses = {UserRepository.class, SmoothieRepository.class, IngredientRepository.class, NutritionalInformationRepository.class})
public class SmoothieWebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmoothieWebsiteApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(UserRepository repository) {
        return (args -> {
        });
    }

}
