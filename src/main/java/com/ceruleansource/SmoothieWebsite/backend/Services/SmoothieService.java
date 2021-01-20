package com.ceruleansource.SmoothieWebsite.backend.Services;

import com.ceruleansource.SmoothieWebsite.backend.Models.Ingredient;
import com.ceruleansource.SmoothieWebsite.backend.Models.NutritionalInformationGrams;
import com.ceruleansource.SmoothieWebsite.backend.Models.NutritionalInformationPercentage;
import com.ceruleansource.SmoothieWebsite.backend.Models.Smoothie;
import com.ceruleansource.SmoothieWebsite.backend.Models.user.User;
import com.ceruleansource.SmoothieWebsite.backend.Repositories.SmoothieRepository;
import com.ceruleansource.SmoothieWebsite.backend.Repositories.UserRepository;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class SmoothieService {
    @Autowired
    SmoothieRepository smoothieRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * @param smoothie - Smoothie to be saved in database
     * @return - returns the smoothie saved in the database (Note: ID is NOT null)
     */
    @Transactional
    public void saveSmoothie(Smoothie smoothie) {
        Optional<User> userOpt = userRepository.findByEmailAndRoles(smoothie.getUser().getEmail(), smoothie.getUser().getRoles());

        userOpt.ifPresentOrElse(user -> user.getSmoothies().add(smoothie), () -> {
            Notification.show("Smoothie Save Service: User not found!").addThemeVariants(NotificationVariant.LUMO_ERROR);
        });
    }

    /**
     * UPDATE: Updates smoothie with ingredient added
     *
     * @param smoothie   - smoothie to add ingredient to
     * @param ingredient - ingredient to add to the smoothie
     */
    @Transactional
    public void addIngredient(Smoothie smoothie, Ingredient ingredient) throws Exception {
        Set<Ingredient> ingredientSet = new HashSet<>(smoothie.getIngredients());
        ingredientSet.add(ingredient);
        smoothie.setIngredients(ingredientSet);
        calcNutriWithAddition(smoothie, ingredient);
        smoothieRepository.save(smoothie);
    }

    /**
     * @param smoothie   - Smoothie to add ingredient to
     * @param ingredient - ingredient to add to smoothie
     * @throws Exception - exception for two nutriGram values that have different units
     */
    private void calcNutriWithAddition(Smoothie smoothie, Ingredient ingredient) throws Exception {
        // Calculate total nutrition
        NutritionalInformationGrams totalGrams = smoothie.getTotalNutritionalInfoGrams();
        totalGrams.addGrams(ingredient.getNutritionalInformationGrams());
        NutritionalInformationPercentage totalPerc = smoothie.getTotalNutritionalInfoPercentage();
        totalPerc.addPercentage(ingredient.getNutritionalInformationPercentage());
    }

    /**
     * UPDATE: Update smoothie with ingredient removed
     *
     * @param smoothie   - smoothie to remove ingredient from
     * @param ingredient - ingredient to remove from smoothie
     * @throws Exception
     */
    @Transactional
    public void removeIngredient(Smoothie smoothie, Ingredient ingredient) throws Exception {
        Set<Ingredient> ingredientSet = new HashSet<>(smoothie.getIngredients());
        ingredientSet.remove(ingredient);
        smoothie.setIngredients(ingredientSet);
        calcNutriWithSubtraction(smoothie, ingredient);
        smoothieRepository.save(smoothie);
    }

    /**
     * @param smoothie   - Smoothie to subtract ingredient from
     * @param ingredient - ingredient to subtract from smoothie
     * @throws Exception - exception for two nutriGram values that have different units
     */
    private void calcNutriWithSubtraction(Smoothie smoothie, Ingredient ingredient) throws Exception {
        // Calculate total nutrition
        NutritionalInformationGrams totalGrams = smoothie.getTotalNutritionalInfoGrams();
        totalGrams.subtractGrams(ingredient.getNutritionalInformationGrams());
        NutritionalInformationPercentage totalPerc = smoothie.getTotalNutritionalInfoPercentage();
        totalPerc.subtractPercentage(ingredient.getNutritionalInformationPercentage());
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
            System.out.println("SmoothieService: " + currentUser);
            if (!currentUser.getSmoothies().isEmpty()) {
                System.out.println("SmoothieService: getUserSmoothies: " + currentUser.getSmoothieNames());
                smoothieSet.addAll(currentUser.getSmoothies());
            }
            //                System.out.println("User does not have any smoothies");
        } else {
            Notification.show("Error! Current user not found.").addThemeVariants(NotificationVariant.LUMO_ERROR);
//            System.out.println("Current user not found");
        }
        return smoothieSet;
    }

    @Transactional
    public boolean checkIfSmoothieNameisTaken(String name, User user) {
        Optional<User> userOptional = userRepository.findById(user.getId());
        if (userOptional.isPresent()) {
            Set<Smoothie> smoothieSet = new HashSet<>(userOptional.get().getSmoothies());
            for (Smoothie smoothie : smoothieSet) {
                if (smoothie.getName().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param smoothie - smoothie to delete
     * @return - a boolean representing the success of the delete function
     * True = deleted smoothie
     * False = error deleting smoothie
     */
    @Transactional
    public void deleteSmoothie(Smoothie smoothie) {
        Optional<User> userOpt = userRepository.findByEmailAndRoles(smoothie.getUser().getEmail(), smoothie.getUser().getRoles());
        userOpt.ifPresentOrElse(
                user -> user.getSmoothies().remove(smoothie),
                () -> Notification.show("Smoothie Deletion Service: User not found!").addThemeVariants(NotificationVariant.LUMO_ERROR));
    }

    @Transactional
    public void deleteSmoothies(Set<Smoothie> smoothieSet) {
        Smoothie firstSmoothie = smoothieSet.iterator().next();
        Optional<User> userOpt = userRepository.findByEmailAndRoles(firstSmoothie.getUser().getEmail(), firstSmoothie.getUser().getRoles());
        userOpt.ifPresentOrElse(user -> user.getSmoothies().removeAll(smoothieSet),
                () -> Notification.show("Smoothie Deletion Service: User not found!").addThemeVariants(NotificationVariant.LUMO_ERROR));
    }

    @Transactional
    public Smoothie getSmoothie(String name, User user) {
        return smoothieRepository.findByNameAndUser(name, user);
    }

    @Transactional
    public Smoothie getSmoothie(Long id){
        Optional<Smoothie> smoothieOptional = smoothieRepository.findById(id);
        return smoothieOptional.orElse(null);
    }

    public boolean updateSmoothie(Smoothie smoothie) {
        Optional<Smoothie> smoothieOpt = smoothieRepository.findById(smoothie.getId());
        if (smoothieOpt.isPresent()) {
            smoothieRepository.save(smoothie);
            return true;
        } else {
            Notification.show("Oops, smoothie did not update in our database!").addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
        return false;
    }
}
