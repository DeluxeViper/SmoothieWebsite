package com.ceruleansource.SmoothieWebsite.backend.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Entity that describes the ingredient's daily percentage values (including vitamins)
 *  - Follows the structure of Nutrition facts obtained from Google's search
 */
@Entity
public class NutritionalInformationPercentage {

    @Id
    @GeneratedValue
    private Long id;

    private String totalFat;

    private String saturatedFat;

    private String polyunsaturatedFat;

    private String monounsaturatedFat;

    private String transFatRegulation;

    private String cholesterol;

    private String sodium;

    private String potassium;

    private String totalCarbohydrates;

    private String dietaryFiber;

    private String sugars;

    private String protein;

    private String caffeine;

    private String vitaminA;

    private String vitaminC;

    private String calcium;

    private String iron;

    private String vitaminD;

    private String vitaminB6;

    private String cobalamin;

    private String magnesium;

    public NutritionalInformationPercentage() {
    }

    public NutritionalInformationPercentage(Long id, String totalFat, String saturatedFat, String polyunsaturatedFat, String monounsaturatedFat, String transFatRegulation, String cholesterol, String sodium, String potassium, String totalCarbohydrates, String dietaryFiber, String sugars, String protein, String caffeine, String vitaminA, String vitaminC, String calcium, String iron, String vitaminD, String vitaminB6, String cobalamin, String magnesium) {
        this.id = id;
        this.totalFat = totalFat;
        this.saturatedFat = saturatedFat;
        this.polyunsaturatedFat = polyunsaturatedFat;
        this.monounsaturatedFat = monounsaturatedFat;
        this.transFatRegulation = transFatRegulation;
        this.cholesterol = cholesterol;
        this.sodium = sodium;
        this.potassium = potassium;
        this.totalCarbohydrates = totalCarbohydrates;
        this.dietaryFiber = dietaryFiber;
        this.sugars = sugars;
        this.protein = protein;
        this.caffeine = caffeine;
        this.vitaminA = vitaminA;
        this.vitaminC = vitaminC;
        this.calcium = calcium;
        this.iron = iron;
        this.vitaminD = vitaminD;
        this.vitaminB6 = vitaminB6;
        this.cobalamin = cobalamin;
        this.magnesium = magnesium;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTransFatRegulation() {
        return transFatRegulation;
    }

    public void setTransFatRegulation(String transFatRegulation) {
        this.transFatRegulation = transFatRegulation;
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

    public String getDietaryFiber() {
        return dietaryFiber;
    }

    public void setDietaryFiber(String dietaryFiber) {
        this.dietaryFiber = dietaryFiber;
    }

    public String getSugars() {
        return sugars;
    }

    public void setSugars(String sugars) {
        this.sugars = sugars;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getCaffeine() {
        return caffeine;
    }

    public void setCaffeine(String caffeine) {
        this.caffeine = caffeine;
    }

    public String getVitaminA() {
        return vitaminA;
    }

    public void setVitaminA(String vitaminA) {
        this.vitaminA = vitaminA;
    }

    public String getVitaminC() {
        return vitaminC;
    }

    public void setVitaminC(String vitaminC) {
        this.vitaminC = vitaminC;
    }

    public String getCalcium() {
        return calcium;
    }

    public void setCalcium(String calcium) {
        this.calcium = calcium;
    }

    public String getIron() {
        return iron;
    }

    public void setIron(String iron) {
        this.iron = iron;
    }

    public String getVitaminD() {
        return vitaminD;
    }

    public void setVitaminD(String vitaminD) {
        this.vitaminD = vitaminD;
    }

    public String getVitaminB6() {
        return vitaminB6;
    }

    public void setVitaminB6(String vitaminB6) {
        this.vitaminB6 = vitaminB6;
    }

    public String getCobalamin() {
        return cobalamin;
    }

    public void setCobalamin(String cobalamin) {
        this.cobalamin = cobalamin;
    }

    public String getMagnesium() {
        return magnesium;
    }

    public void setMagnesium(String magnesium) {
        this.magnesium = magnesium;
    }

    @Override
    public String toString() {
        return "NutritionInformationPercentage{" +
                "id=" + id +
                ", totalFat=" + totalFat +
                ", saturatedFat=" + saturatedFat +
                ", polyunsaturatedFat=" + polyunsaturatedFat +
                ", monounsaturatedFat=" + monounsaturatedFat +
                ", transFatRegulation=" + transFatRegulation +
                ", cholesterol=" + cholesterol +
                ", sodium=" + sodium +
                ", potassium=" + potassium +
                ", totalCarbohydrates=" + totalCarbohydrates +
                ", dietaryFiber=" + dietaryFiber +
                ", sugars=" + sugars +
                ", protein=" + protein +
                ", caffeine=" + caffeine +
                ", vitaminA=" + vitaminA +
                ", vitaminC=" + vitaminC +
                ", calcium=" + calcium +
                ", iron=" + iron +
                ", vitaminD=" + vitaminD +
                ", vitaminB6=" + vitaminB6 +
                ", cobalamin=" + cobalamin +
                ", magnesium=" + magnesium +
                '}';
    }
}
