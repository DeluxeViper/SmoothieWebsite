package com.ceruleansource.SmoothieWebsite.backend.Models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String quantityTypeAndValue;

    @OneToOne
    private NutritionalInformationGrams nutritionalInformationGrams;

    @OneToOne
    private NutritionalInformationPercentage nutritionalInformationPercentage;

    @ManyToMany(mappedBy = "ingredients", fetch = FetchType.EAGER)
    private List<Smoothie> smoothies = new ArrayList<>();

    public Ingredient(){
    }

    public Ingredient(Long id, String name, String quantityTypeAndValue, NutritionalInformationGrams nutritionalInformationGrams, NutritionalInformationPercentage nutritionalInformationPercentage) {
        this.id = id;
        this.name = name;
        this.quantityTypeAndValue = quantityTypeAndValue;
        this.nutritionalInformationGrams = nutritionalInformationGrams;
        this.nutritionalInformationPercentage = nutritionalInformationPercentage;
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

    public List<Smoothie> getSmoothies() {
        return smoothies;
    }

    public void setSmoothie(List<Smoothie> smoothies) {
        this.smoothies = smoothies;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantityTypeAndValue='" + quantityTypeAndValue + '\'' +
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
