package com.ceruleansource.SmoothieWebsite;

import com.ceruleansource.SmoothieWebsite.backend.Authentication.UserSession;
import com.ceruleansource.SmoothieWebsite.backend.Models.Ingredient;
import com.ceruleansource.SmoothieWebsite.backend.Models.Smoothie;
import com.ceruleansource.SmoothieWebsite.backend.Models.user.User;
import com.ceruleansource.SmoothieWebsite.backend.Repositories.IngredientRepository;
import com.ceruleansource.SmoothieWebsite.backend.Repositories.SmoothieRepository;
import com.ceruleansource.SmoothieWebsite.backend.Repositories.UserRepository;
import org.hibernate.LazyInitializationException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.parameters.P;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
//@EnableJpaRepositories(basePackageClasses = {UserRepository.class, SmoothieRepository.class, IngredientRepository.class, NutritionalInformationRepository.class})
public class SmoothieWebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmoothieWebsiteApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(UserRepository userRepository, SmoothieRepository smoothieRepository, IngredientRepository ingredientRepository) {
        return (args -> {

//            if (userRepository.findByEmail("h@gmail.com").isPresent()){
//                // Adding smoothie to user
//                User user = userRepository.findByEmail("h@gmail.com").get();
//                Smoothie smoothie = new Smoothie("Smoothie", user);
//                Set<Smoothie> set = new HashSet<>();
//                set.add(smoothie);
//                user.setSmoothies(set);
//                userRepository.save(user);
//                Iterable<Smoothie> smoothies = smoothieRepository.findAll();
//                Smoothie firstSmoothie = smoothies.iterator().next();
//                if (smoothieRepository.findById(firstSmoothie.getId()).isPresent()){
//                    // Add ingredients to smoothie
//                    Smoothie smoothie1 = smoothieRepository.findById(firstSmoothie.getId()).get();
//                    Ingredient almondButter = ingredientRepository.findById(4L).get();
//                    Ingredient appleJuice = ingredientRepository.findById(5L).get();
//                    Set<Ingredient> ingredients = new HashSet<>();
//                    ingredients.add(almondButter);
//                    ingredients.add(appleJuice);
//                    smoothie1.setIngredients(ingredients);
//                    set.clear();
//                    set.add(smoothie1);
//                    // Saving smoothie directly to the smoothie repo
//                    smoothieRepository.save(smoothie1);
//                    System.out.println("Smoothie being saved: " + smoothie1);
//                    System.out.println("User after smoothie updated: " + user);
//                    // Removing ingredient flow
//                    if (smoothie1.getIngredients().contains(almondButter)){
//                        System.out.println("Almond butter is present in smoothie");
//                        smoothie1.getIngredients().remove(almondButter);
//                        System.out.println("Smoothie after removing ingredient without saving:\n" + smoothie1);
//                        smoothieRepository.save(smoothie1);
//                        System.out.println("Smoothie after saving: " + smoothieRepository.findById(firstSmoothie.getId()));
//                    } else {
//                        System.out.println("Almond butter is not present in smoothie");
//                    }
//
//                    System.out.println("Duplicate check in set after adding ingredients:\n" + set);
//
//                    // Check if updating the smoothie updates the user with smoothie in the database
////                    User user2 = userRepository.findByEmail("h@gmail.com").get();
//
////                    try {
////                        System.out.println("User: " + user2.toString());
////                    } catch (LazyInitializationException e){
////                        userRepository.save(user2);
////                        System.out.println("Caught exception & now printing user: " + user2);
////                    }
//                    // Saving smoothie by saving to user
////                    user.setSmoothies(set);
////                    userRepository.save(user);
//                }
//            }
        });
    }

}
