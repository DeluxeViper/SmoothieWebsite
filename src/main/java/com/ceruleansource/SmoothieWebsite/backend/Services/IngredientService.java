package com.ceruleansource.SmoothieWebsite.backend.Services;

import com.ceruleansource.SmoothieWebsite.backend.Models.Ingredient;
import com.ceruleansource.SmoothieWebsite.backend.Models.Smoothie;
import com.ceruleansource.SmoothieWebsite.backend.Repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Notification;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {

    @Autowired
    IngredientRepository ingredientRepository;
    @Autowired
    SmoothieService smoothieService;

    public Ingredient findIngredient(String name, String type, double multiplier){
        return ingredientRepository.findDistinctByNameAndMultiplierAndQuantityTypeAndValue(name, type, multiplier);
    }

    public void updateIngredientMultiplier(Smoothie selectedSmoothie, Ingredient ingredient, double multiplier) {
        Optional<Ingredient> ingredientToChange = ingredientRepository.findById(ingredient.getId());
        ingredientToChange.ifPresent(ingredient1 -> {
            if (ingredient1.getMultiplier() == 1){
                // Saving another ingredient with 1 multiplier for the quantity types to be displayed
                //  (due to the fact that if any ingredient with a 1 multiplier is changed, then that quantity type will cease to exist
                // update ingredient within selected smoothie with a new one so we don't have to modify the ingredient with multiplier 1
                try {
                    smoothieService.removeIngredient(selectedSmoothie, ingredient1);
                    Ingredient newMultiplierIngredient = new Ingredient(ingredient1.getName(), ingredient1.getQuantityTypeAndValue(), ingredient1.getMultiplier() + multiplier, ingredient1.getNutritionalInformationGrams(), ingredient1.getNutritionalInformationPercentage());
                    Ingredient foundIngredient;
                    if ((foundIngredient = findIngredient(newMultiplierIngredient.getName(), newMultiplierIngredient.getQuantityTypeAndValue(), newMultiplierIngredient.getMultiplier())) != null) {
                        // Ingredient exists in database
                        smoothieService.addIngredient(selectedSmoothie, foundIngredient);
                    } else {
                        // Ingredient doesn't exist in database
                        Ingredient savedIngredient = ingredientRepository.save(newMultiplierIngredient); // saving ingredient
                        smoothieService.addIngredient(selectedSmoothie, savedIngredient);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                ingredient1.setMultiplier(ingredient1.getMultiplier() + multiplier);
                Ingredient savedIngredient = ingredientRepository.save(ingredient1);
            }
        });
    }

    public Ingredient saveIngredient(Ingredient ingredient) {
        Ingredient uniqueIngredient = ingredientRepository.findDistinctByNameAndMultiplierAndQuantityTypeAndValue(ingredient.getName(), ingredient.getQuantityTypeAndValue(), ingredient.getMultiplier());
        if (uniqueIngredient == null) {
            // Ingredient is not found in database, saving new ingredient
            Ingredient ingredient1 = ingredientRepository.save(ingredient);

            if (ingredient1 == null) {
                System.out.println("Ingredient not saved!");
                return null;
            } else {
                System.out.println("Ingredient successfully saved: " + ingredient1);
                return ingredient1;
            }
        } else {
            // Ingredient match found in database, returning it
            return uniqueIngredient;
        }
    }

    public List<Ingredient> findAllIngredientsWithName(String name) {
        Iterable<Ingredient> ingredientIterable = ingredientRepository.findAllByName(name);
        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientIterable.forEach(ingredientList::add);
        System.out.println("Ingredients list: " + ingredientList);
        return ingredientList;
    }

    public List<Ingredient> fetchAll() {
        Iterable<Ingredient> ingredientsIterable = ingredientRepository.findAll();
        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientsIterable.forEach(ingredient -> {
            ingredientList.add(ingredient);
        });

        return ingredientList;
    }

    public List<String> findAllDistinctNamedIngredients() {
        return ingredientRepository.findAllDistinctName();
    }

    public List<String> findAllDistinctNamedIngredientsWithSingleMultiplier() {
        System.out.println("Multiplier1: " + ingredientRepository.findAllDistinctNameWhereMultiplierIs1());
        return ingredientRepository.findAllDistinctNameWhereMultiplierIs1();
    }

    public Optional<Ingredient> getIngredient(Long id) {
        return ingredientRepository.findById(id);
    }

    public Optional<Ingredient> getIngredient(Long id, double multiplier) {
        return ingredientRepository.findByIdAndMultiplier(id, multiplier);
    }

    public int getCount() {
        return (int) ingredientRepository.count();
    }
}
