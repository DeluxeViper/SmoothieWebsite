package com.ceruleansource.SmoothieWebsite.backend.Services;

import com.ceruleansource.SmoothieWebsite.backend.Models.Ingredient;
import com.ceruleansource.SmoothieWebsite.backend.Models.Smoothie;
import com.ceruleansource.SmoothieWebsite.backend.Models.user.User;
import com.ceruleansource.SmoothieWebsite.backend.Repositories.SmoothieRepository;
import com.ceruleansource.SmoothieWebsite.backend.Repositories.UserRepository;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SmoothieService {
    @Autowired
    SmoothieRepository smoothieRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public boolean saveSmoothie(Smoothie smoothie) {
        Optional<User> currentUserOptional = userRepository.findByEmail(smoothie.getUser().getEmail());
        if (currentUserOptional.isPresent()) {
            User currentUser = currentUserOptional.get();
            currentUser.getSmoothies().add(smoothie);
            User savedUser = userRepository.save(currentUser);
            System.out.println("smoothieService: saveSmoothie: User: " + currentUser);
            return savedUser.getSmoothies().contains(smoothie);
        } else {
            return false;
        }
    }

    /**
     * UPDATE: Updates smoothie with ingredient added
     * If smoothie is not contained in current user, then save smoothie to user
     *
     * @param smoothie   - smoothie to add ingredient to
     * @param ingredient - ingredient to add to the smoothie
     */
    @Transactional
    public void addIngredient(Smoothie smoothie, Ingredient ingredient) {
        Optional<User> currentUserOptional = userRepository.findByEmail(smoothie.getUser().getEmail());
        if (currentUserOptional.isPresent()) {
            System.out.println("Hey");
            User currentUser = currentUserOptional.get();
            if (currentUser.getSmoothies().contains(smoothie)) {
                System.out.println("User contained smoothie");
                // smoothie is saved in user
                if (!smoothie.getIngredients().contains(ingredient)) {
                    System.out.println("Smoothie didn't contain ingredient --> adding ingredient");
                    System.out.println("Trying to insert: " + ingredient);
                    System.out.println("Smoothe before adding ingredient: ");
                    smoothie.getIngredients().forEach(ingr -> System.out.print(ingr.getName() + ", "));
                    System.out.println();
                    smoothie.getIngredients().add(ingredient);
//                    smoothieRepository.save(smoothie);
                    System.out.println("Smoothie after adding ingredient: ");
                    smoothie.getIngredients().forEach(ingr -> System.out.print(ingr.getName() + ", "));
                    System.out.println();
                } else {
                    Notification.show("Smoothie already contains that ingredient!").addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            } else {
                // If smoothie is not saved in user
                currentUser.getSmoothies().add(smoothie);
                userRepository.save(currentUser);
                System.out.println("Smoothie did not exist in user so saved it");
                smoothie.getIngredients().add(ingredient);
                smoothieRepository.save(smoothie);
            }
        }
    }

    @Transactional
    public void removeIngredient(Smoothie smoothie, Ingredient ingredient) {
        Optional<User> currentUserOptional = userRepository.findByEmail(smoothie.getUser().getEmail());
        if (currentUserOptional.isPresent()) {
            System.out.println("User is present");
            User currentUser = currentUserOptional.get();
            if (currentUser.getSmoothies().contains(smoothie)) {
                System.out.println("Current user conatins smoothie");
                // Current user has smoothie to remove ingredient from
                System.out.println("Smoothie before removing ingredient: ");
                smoothie.getIngredients().forEach(ingr -> System.out.print(ingr.getName() + ", "));
                System.out.println();

                if (smoothie.getIngredients().contains(ingredient)) {
                    System.out.println("smoothie contains ingredient");
                    smoothie.getIngredients().remove(ingredient);
//                    smoothieRepository.save(smoothie);
                } else {
                    System.out.println("smoothie does not contain ingredient");
                    System.out.println("-------------------------");
                    System.out.println("Ingredient we're trying to remove: " + ingredient);
                    System.out.println("Ingredients in smoothie: " + smoothie.getIngredients());
                }
                System.out.println("Smoothie after removing ingredient: " + smoothie);
                smoothie.getIngredients().forEach(ingr -> System.out.print(ingr.getName() + ", "));
                System.out.println();
            } else {
                System.out.println("Current user does not contain smoothie");
                // Current user does not have smoothie to remove ingredient from
                // Probably wont need this
            }
        } else {
            System.out.println("User is not present");
        }
    }

    /**
     * GET: Smoothies in current user
     *
     * @param user - Current user logged in
     * @return - returns set of smoothies contained in current user
     */
    @Transactional
    public Set<Smoothie> getSmoothiesForCurrentUser(User user) {
        Optional<User> currentUserOptional = userRepository.findByEmail(user.getEmail());
        Set<Smoothie> smoothieSet = new HashSet<>();
        if (currentUserOptional.isPresent()) {
//            System.out.println("User is present");
            User currentUser = currentUserOptional.get();
            if (!currentUser.getSmoothies().isEmpty()) {
                smoothieSet.addAll(currentUser.getSmoothies());
            } else {
//                System.out.println("User does not have any smoothies");
            }
        } else {
            Notification.show("Error! Current user not found.").addThemeVariants(NotificationVariant.LUMO_ERROR);
//            System.out.println("Current user not found");
        }
        return smoothieSet;
    }

    /**
     *
     * @param smoothie - smoothie to delete
     * @return - a boolean representing the success of the delete function
     *              True = deleted smoothie
     *              False = error deleting smoothie
     */
    @Transactional
    public boolean deleteSmoothie(Smoothie smoothie) {
        Optional<User> currentUserOptional = userRepository.findByEmail(smoothie.getUser().getEmail());
        if (currentUserOptional.isPresent()) {
            User currentUser = currentUserOptional.get();
            if (currentUser.getSmoothies().contains(smoothie)) {
                currentUser.getSmoothies().remove(smoothie);
                userRepository.save(currentUser);
                return !currentUser.getSmoothies().contains(smoothie);
            } else {
                Notification.show("Error! Smoothie was not found in user").addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        } else {
            Notification.show("Error! User was not found").addThemeVariants(NotificationVariant.LUMO_ERROR);
            return false;
        }
        return false;
    }
}
