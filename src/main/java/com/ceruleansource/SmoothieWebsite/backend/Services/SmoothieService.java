package com.ceruleansource.SmoothieWebsite.backend.Services;

import com.ceruleansource.SmoothieWebsite.backend.Authentication.UserSession;
import com.ceruleansource.SmoothieWebsite.backend.Models.Ingredient;
import com.ceruleansource.SmoothieWebsite.backend.Models.Smoothie;
import com.ceruleansource.SmoothieWebsite.backend.Models.user.User;
import com.ceruleansource.SmoothieWebsite.backend.Repositories.SmoothieRepository;
import com.ceruleansource.SmoothieWebsite.backend.Repositories.UserRepository;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SmoothieService {
    @Autowired
    SmoothieRepository smoothieRepository;

    @Autowired
    UserRepository userRepository;

    public void removeIngredientFromSmoothie(Long id) {

    }

    public void addIngredient(User user, Smoothie smoothie, Ingredient ingredient) {
        // Find user

        // Check if smoothie exists in user
        Optional<Smoothie> smoothieOptional = smoothieRepository.findById(smoothie.getId());
        smoothieOptional.ifPresentOrElse(
                smoothie1 -> {
                    smoothie1.getIngredients().add(ingredient);
                    smoothieRepository.save(smoothie1);
                },
                () -> {
                    smoothie.getIngredients().add(ingredient);
                    smoothieRepository.save(smoothie);
                }
        );
    }

    public List<Ingredient> getAllIngredients(Smoothie smoothie) {
        Optional<Smoothie> smoothieOptional = smoothieRepository.findById(smoothie.getId());
        List<Ingredient> ingredientList = new ArrayList<>();

        smoothieOptional.ifPresentOrElse(
                smoothie1 -> {
                    // If smoothie is present in database then get all ingredients
                    ingredientList.addAll(smoothie1.getIngredients());
                },
                () -> {
                    // If smoothie is missing from database, save the smoothie & return an empty list
                    smoothieRepository.save(smoothie);
                }
        );

        return ingredientList;
    }

    public void addIngredient(Long smoothieId) {

    }

    public void addSmoothieToUser(Smoothie smoothie){

    }
    /**
     * UPDATE
     *
     * @param user     - current user to add a smoothie for
     * @param smoothie - smoothie to add to the user
     */
    public void addSmoothieToUser(User user, Smoothie smoothie) {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());

        optionalUser.orElseThrow(() -> new UsernameNotFoundException("User not found in database!"));

        optionalUser.ifPresent(user1 -> {
            if (!smoothieRepository.existsById(smoothie.getId())) {
                smoothieRepository.save(smoothie);
            }
            user1.addSmoothie(smoothie);
            userRepository.save(user1);
        });
    }

    /**
     * UPDATE
     *
     * @param id       - id of current user to add smoothie for
     * @param smoothie - smoothie to add to the user
     */
    public void addSmoothieToUser(Long id, Smoothie smoothie) {
        Optional<User> optionalUser = userRepository.findById(id);

        optionalUser.orElseThrow(() -> new UsernameNotFoundException("User not found in database!"));

        optionalUser.ifPresent(user1 -> {
            user1.addSmoothie(smoothie);
            userRepository.save(user1);
        });
    }
}
