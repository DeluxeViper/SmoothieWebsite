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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

@Service
public class SmoothieService {
    @Autowired
    SmoothieRepository smoothieRepository;

    @Autowired
    UserRepository userRepository;

    /**
     *
     * @param smoothie - Smoothie to be saved in database
     * @return - returns the smoothie saved in the database (Note: ID is NOT null)
     */
    @Transactional
    public void saveSmoothie(Smoothie smoothie) {
        Optional<User> currentUserOptional = userRepository.findByEmailAndRoles(smoothie.getUser().getEmail(), smoothie.getUser().getRoles());

        if (currentUserOptional.isPresent()) {
            System.out.println(currentUserOptional.get());
            User currentUser = currentUserOptional.get();
            Set<Smoothie> smoothieSet = new HashSet<>(currentUser.getSmoothies());
            smoothieSet.add(smoothie);
            currentUser.setSmoothies(smoothieSet);
            userRepository.save(currentUser);
        }
    }

    /**
     * UPDATE: Updates smoothie with ingredient added
     * If smoothie is not contained in current user, then save smoothie to user
     *
     * @param smoothie   - smoothie to add ingredient to
     * @param ingredient - ingredient to add to the smoothie
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addIngredient(Smoothie smoothie, Ingredient ingredient) {
        Set<Ingredient> ingredientSet = new HashSet<>(smoothie.getIngredients());
        ingredientSet.add(ingredient);
        smoothie.setIngredients(ingredientSet);
        smoothieRepository.save(smoothie);
    }

    @Transactional
    public void removeIngredient(Smoothie smoothie, Ingredient ingredient) {
        Set<Ingredient> ingredientSet = new HashSet<>(smoothie.getIngredients());
        ingredientSet.remove(ingredient);
        smoothie.setIngredients(ingredientSet);
        smoothieRepository.save(smoothie);
    }

    /**
     * GET: Smoothies in current user
     *
     * @param user - Current user logged in
     * @return - returns set of smoothies contained in current user
     */
    @Transactional
    public Set<Smoothie> getSmoothiesForCurrentUser(User user) {
        Optional<User> currentUserOptional = userRepository.findByEmailAndRoles(user.getEmail(), user.getRoles());
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

    @Transactional
    public boolean checkIfSmoothieNameisTaken(String name, User user){
        Optional<User> userOptional = userRepository.findById(user.getId());
        if (userOptional.isPresent()){
            Set<Smoothie> smoothieSet = new HashSet<>(userOptional.get().getSmoothies());
            for (Smoothie smoothie : smoothieSet){
                if (smoothie.getName().equals(name)){
                    return true;
                }
            }
        }

        return false;
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
        Optional<User> currentUserOptional = userRepository.findByEmailAndRoles(smoothie.getUser().getEmail(), smoothie.getUser().getRoles());
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

    @Transactional
    public Smoothie getSmoothie(String name, User user){
        return smoothieRepository.findByNameAndUser(name, user);
    }
}
