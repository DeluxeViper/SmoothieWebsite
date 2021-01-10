package com.ceruleansource.SmoothieWebsite.backend.Repositories;

import com.ceruleansource.SmoothieWebsite.backend.Models.Ingredient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {

    List<Ingredient> findAllByName(String name);

    @Query("SELECT DISTINCT ingr.name from Ingredient ingr")
    List<String> findAllDistinctName();

}
