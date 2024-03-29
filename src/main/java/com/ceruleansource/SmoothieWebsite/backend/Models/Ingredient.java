package com.ceruleansource.SmoothieWebsite.backend.Models;

import javax.persistence.*;
import java.util.*;

@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    private Long id;

    private String name;

    private String quantityTypeAndValue;

    private double multiplier;

    @OneToOne
    private NutritionalInformationGrams nutritionalInformationGrams;

    @OneToOne
    private NutritionalInformationPercentage nutritionalInformationPercentage;

    @ManyToMany(mappedBy = "ingredients", cascade = CascadeType.ALL)
    private Set<Smoothie> smoothies  = new HashSet<>();

    public Ingredient(){
    }

    public Ingredient(String name, String quantityTypeAndValue, double multiplier, NutritionalInformationGrams nutritionalInformationGrams, NutritionalInformationPercentage nutritionalInformationPercentage) {
        this.name = name;
        this.quantityTypeAndValue = quantityTypeAndValue;
        this.nutritionalInformationGrams = nutritionalInformationGrams;
        this.nutritionalInformationPercentage = nutritionalInformationPercentage;
        this.multiplier = multiplier;
    }

    public Ingredient(Long id, String name, String quantityTypeAndValue, double multiplier, NutritionalInformationGrams nutritionalInformationGrams, NutritionalInformationPercentage nutritionalInformationPercentage) {
        this.id = id;
        this.name = name;
        this.quantityTypeAndValue = quantityTypeAndValue;
        this.nutritionalInformationGrams = nutritionalInformationGrams;
        this.nutritionalInformationPercentage = nutritionalInformationPercentage;
        this.multiplier = multiplier;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantityTypeAndValue() {
        return quantityTypeAndValue;
    }

    public void setQuantityTypeAndValue(String quantityTypeAndValue) {
        this.quantityTypeAndValue = quantityTypeAndValue;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    public NutritionalInformationGrams getNutritionalInformationGrams() {
        return nutritionalInformationGrams;
    }

    public void setNutritionalInformationGrams(NutritionalInformationGrams nutritionalInformation) {
        this.nutritionalInformationGrams = nutritionalInformation;
    }

    public NutritionalInformationPercentage getNutritionalInformationPercentage() {
        return nutritionalInformationPercentage;
    }

    public void setNutritionalInformationPercentage(NutritionalInformationPercentage nutritionalInformationPercentage) {
        this.nutritionalInformationPercentage = nutritionalInformationPercentage;
    }

    public Set<Smoothie> getSmoothies() {
        return smoothies;
    }

    public void setSmoothies(Set<Smoothie> smoothies) {
        this.smoothies = smoothies;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantityTypeAndValue='" + quantityTypeAndValue + '\'' +
                ", multiplier=" + multiplier +
                ", nutritionalInformationGrams=" + nutritionalInformationGrams +
                ", nutritionalInformationPercentage=" + nutritionalInformationPercentage +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}