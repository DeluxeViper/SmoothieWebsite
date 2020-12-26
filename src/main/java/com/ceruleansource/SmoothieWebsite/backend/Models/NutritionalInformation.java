package com.ceruleansource.SmoothieWebsite.backend.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class NutritionalInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int calories;

    private String totalFat;

    private String saturatedFat;

    private String polyunsaturatedFat;

    private String monounsaturatedFat;

    private String cholesterol;

    private String sodium;

    private String potassium;

    private String totalCarbohydrates;

    private String dietaryFibre;

    private String protein;

    private String vitaminA;

    private String vitaminB;

    private String vitaminC;

    public NutritionalInformation() {
    }

    public NutritionalInformation(Long id, int calories, String totalFat, String saturatedFat, String polyunsaturatedFat, String monounsaturatedFat, String cholesterol, String sodium, String potassium, String totalCarbohydrates, String dietaryFibre, String protein, String vitaminA, String vitaminB, String vitaminC) {
        this.id = id;
        this.calories = calories;
        this.totalFat = totalFat;
        this.saturatedFat = saturatedFat;
        this.polyunsaturatedFat = polyunsaturatedFat;
        this.monounsaturatedFat = monounsaturatedFat;
        this.cholesterol = cholesterol;
        this.sodium = sodium;
        this.potassium = potassium;
        this.totalCarbohydrates = totalCarbohydrates;
        this.dietaryFibre = dietaryFibre;
        this.protein = protein;
        this.vitaminA = vitaminA;
        this.vitaminB = vitaminB;
        this.vitaminC = vitaminC;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getTotalFat() {
        return totalFat;
    }

    public void setTotalFat(String totalFat) {
        this.totalFat = totalFat;
    }

    public String getSaturatedFat() {
        return saturatedFat;
    }

    public void setSaturatedFat(String saturatedFat) {
        this.saturatedFat = saturatedFat;
    }

    public String getPolyunsaturatedFat() {
        return polyunsaturatedFat;
    }

    public void setPolyunsaturatedFat(String polyunsaturatedFat) {
        this.polyunsaturatedFat = polyunsaturatedFat;
    }

    public String getMonounsaturatedFat() {
        return monounsaturatedFat;
    }

    public void setMonounsaturatedFat(String monounsaturatedFat) {
        this.monounsaturatedFat = monounsaturatedFat;
    }

    public String getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(String cholesterol) {
        this.cholesterol = cholesterol;
    }

    public String getSodium() {
        return sodium;
    }

    public void setSodium(String sodium) {
        this.sodium = sodium;
    }

    public String getPotassium() {
        return potassium;
    }

    public void setPotassium(String potassium) {
        this.potassium = potassium;
    }

    public String getTotalCarbohydrates() {
        return totalCarbohydrates;
    }

    public void setTotalCarbohydrates(String totalCarbohydrates) {
        this.totalCarbohydrates = totalCarbohydrates;
    }

    public String getDietaryFibre() {
        return dietaryFibre;
    }

    public void setDietaryFibre(String dietaryFibre) {
        this.dietaryFibre = dietaryFibre;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getVitaminA() {
        return vitaminA;
    }

    public void setVitaminA(String vitaminA) {
        this.vitaminA = vitaminA;
    }

    public String getVitaminB() {
        return vitaminB;
    }

    public void setVitaminB(String vitaminB) {
        this.vitaminB = vitaminB;
    }

    public String getVitaminC() {
        return vitaminC;
    }

    public void setVitaminC(String vitaminC) {
        this.vitaminC = vitaminC;
    }

    @Override
    public String toString() {
        return "NutritionalInformation{" +
                "id=" + id +
                ", calories=" + calories +
                ", totalFat='" + totalFat + '\'' +
                ", saturatedFat='" + saturatedFat + '\'' +
                ", polyunsaturatedFat='" + polyunsaturatedFat + '\'' +
                ", monounsaturatedFat='" + monounsaturatedFat + '\'' +
                ", cholesterol='" + cholesterol + '\'' +
                ", sodium='" + sodium + '\'' +
                ", potassium='" + potassium + '\'' +
                ", totalCarbohydrates='" + totalCarbohydrates + '\'' +
                ", dietaryFibre='" + dietaryFibre + '\'' +
                ", protein='" + protein + '\'' +
                ", vitaminA='" + vitaminA + '\'' +
                ", vitaminB='" + vitaminB + '\'' +
                ", vitaminC='" + vitaminC + '\'' +
                '}';
    }
}
