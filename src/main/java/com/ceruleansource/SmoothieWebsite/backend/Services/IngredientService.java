package com.ceruleansource.SmoothieWebsite.backend.Services;

import com.ceruleansource.SmoothieWebsite.backend.Models.Ingredient;
import com.ceruleansource.SmoothieWebsite.backend.Repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {

    @Autowired
    IngredientRepository ingredientRepository;

    public List<Ingredient> findAllIngredientsWithName(String name){
        Iterable<Ingredient> ingredientIterable = ingredientRepository.findAllByName(name);
        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientIterable.forEach(ingredientList::add);
        return ingredientList;
    }

    public List<String> getAllIngredientNames(){
        Iterable<Ingredient> ingredientIterable = ingredientRepository.findAll();
        List<String> ingredientNames = new ArrayList<>();
        ingredientIterable.forEach(ingredient -> {
            ingredientNames.add(ingredient.getName());
        });

        return ingredientNames;
    }

    public List<Ingredient> fetchAll(){
        Iterable<Ingredient> ingredientsIterable = ingredientRepository.findAll();
        List<Ingredient> ingredientList = new ArrayList<>();
        System.out.println("Ingredient Reposss: " + ingredientRepository.findAll());
        ingredientsIterable.forEach(ingredient -> {
            ingredientList.add(ingredient);
        });
        System.out.println("Ingredients Listssss: " + ingredientList);

        return ingredientList;
    }

    public int getCount(){
        return (int) ingredientRepository.count();
    }
}
