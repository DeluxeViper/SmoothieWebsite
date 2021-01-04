package com.ceruleansource.SmoothieWebsite;

import com.ceruleansource.SmoothieWebsite.backend.Repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
