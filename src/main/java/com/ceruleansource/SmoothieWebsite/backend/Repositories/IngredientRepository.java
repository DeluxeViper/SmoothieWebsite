package com.ceruleansource.SmoothieWebsite.backend.Repositories;

import com.ceruleansource.SmoothieWebsite.backend.Models.Ingredient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {

    @Query("SELECT ingr from Ingredient ingr where ingr.multiplier = 1.0 and ingr.name = ?1")
    List<Ingredient> findAllByName(String name);

    @Query("SELECT DISTINCT ingr.name from Ingredient ingr where ingr.multiplier = 1.0")
    List<String> findAllDistinctNameWhereMultiplierIs1();

    @Query("SELECT DISTINCT ingr.name from Ingredient ingr")
    List<String> findAllDistinctName();

    @Query("SELECT DISTINCT ingr from Ingredient ingr where ingr.name = ?1 and ingr.quantityTypeAndValue = ?2 and ingr.multiplier = ?3")
    Ingredient findDistinctByNameAndMultiplierAndQuantityTypeAndValue(String name, String type, double multiplier);

    Optional<Ingredient> findByIdAndMultiplier(Long id, double multiplier);
}
