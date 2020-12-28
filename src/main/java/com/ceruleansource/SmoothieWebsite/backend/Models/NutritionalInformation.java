package com.ceruleansource.SmoothieWebsite.backend.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class NutritionalInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int calories;

    private double totalFat;

    private double saturatedFat;

    private double polyunsaturatedFat;

    private double monounsaturatedFat;

    private double cholesterol;

    private double sodium;

    private double potassium;

    private double totalCarbohydrates;

    private double dietaryFibre;

    private double sugars;

    private double protein;

    private double vitaminA;

    private double vitaminB;

    private double vitaminC;

    private double calcium;

    private double iron;

    public NutritionalInformation() {
    }

    public NutritionalInformation(int calories, double totalFat, double saturatedFat, double polyunsaturatedFat,
                                  double monounsaturatedFat, double cholesterol, double sodium, double potassium,
                                  double totalCarbohydrates, double dietaryFibre, double sugars, double protein,
                                  double vitaminA, double vitaminB, double vitaminC, double calcium, double iron) {
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
        this.sugars = sugars;
        this.protein = protein;
        this.vitaminA = vitaminA;
        this.vitaminB = vitaminB;
        this.vitaminC = vitaminC;
        this.calcium = calcium;
        this.iron = iron;
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

    public double getTotalFat() {
        return totalFat;
    }

    public void setTotalFat(double totalFat) {
        this.totalFat = totalFat;
    }

    public double getSaturatedFat() {
        return saturatedFat;
    }

    public void setSaturatedFat(double saturatedFat) {
        this.saturatedFat = saturatedFat;
    }

    public double getPolyunsaturatedFat() {
        return polyunsaturatedFat;
    }

    public void setPolyunsaturatedFat(double polyunsaturatedFat) {
        this.polyunsaturatedFat = polyunsaturatedFat;
    }

    public double getMonounsaturatedFat() {
        return monounsaturatedFat;
    }

    public void setMonounsaturatedFat(double monounsaturatedFat) {
        this.monounsaturatedFat = monounsaturatedFat;
    }

    public double getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(double cholesterol) {
        this.cholesterol = cholesterol;
    }

    public double getSodium() {
        return sodium;
    }

    public void setSodium(double sodium) {
        this.sodium = sodium;
    }

    public double getPotassium() {
        return potassium;
    }

    public void setPotassium(double potassium) {
        this.potassium = potassium;
    }

    public double getTotalCarbohydrates() {
        return totalCarbohydrates;
    }

    public void setTotalCarbohydrates(double totalCarbohydrates) {
        this.totalCarbohydrates = totalCarbohydrates;
    }

    public double getDietaryFibre() {
        return dietaryFibre;
    }

    public void setDietaryFibre(double dietaryFibre) {
        this.dietaryFibre = dietaryFibre;
    }

    public double getSugars() {
        return sugars;
    }

    public void setSugars(double sugars) {
        this.sugars = sugars;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getVitaminA() {
        return vitaminA;
    }

    public void setVitaminA(double vitaminA) {
        this.vitaminA = vitaminA;
    }

    public double getVitaminB() {
        return vitaminB;
    }

    public void setVitaminB(double vitaminB) {
        this.vitaminB = vitaminB;
    }

    public double getVitaminC() {
        return vitaminC;
    }

    public void setVitaminC(double vitaminC) {
        this.vitaminC = vitaminC;
    }

    public double getCalcium() {
        return calcium;
    }

    public void setCalcium(double calcium) {
        this.calcium = calcium;
    }

    public double getIron() {
        return iron;
    }

    public void setIron(double iron) {
        this.iron = iron;
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
