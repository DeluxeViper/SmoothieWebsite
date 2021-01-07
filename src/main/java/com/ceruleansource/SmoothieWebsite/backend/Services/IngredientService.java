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

    public List<Ingredient> fetchAll(){
        Iterable<Ingredient> ingredientsIterable = ingredientRepository.findAll();
        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientsIterable.forEach(ingredient -> {
            ingredientList.add(ingredient);
        });

        return ingredientList;
    }

    public Optional<Ingredient> getIngredient(Long id){
        return ingredientRepository.findById(id);
    }

    public int getCount(){
        return (int) ingredientRepository.count();
    }
}
