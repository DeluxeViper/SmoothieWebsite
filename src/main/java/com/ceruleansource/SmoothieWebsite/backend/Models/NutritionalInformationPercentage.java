package com.ceruleansource.SmoothieWebsite.backend.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Entity that describes the ingredient's daily percentage values (including vitamins)
 *  - Follows the structure of Nutrition facts obtained from Google's search
 */
@Entity
public class NutritionalInformationPercentage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
        this.totalFat = "0%";
        this.saturatedFat = "0%";
        this.polyunsaturatedFat = "0%";
        this.monounsaturatedFat = "0%";
        this.transFatRegulation = "0%";
        this.cholesterol = "0%";
        this.sodium = "0%";
        this.potassium = "0%";
        this.totalCarbohydrates = "0%";
        this.dietaryFiber = "0%";
        this.sugars = "0%";
        this.protein = "0%";
        this.caffeine = "0%";
        this.vitaminA = "0%";
        this.vitaminC = "0%";
        this.calcium = "0%";
        this.iron = "0%";
        this.vitaminD = "0%";
        this.vitaminB6 = "0%";
        this.cobalamin = "0%";
        this.magnesium = "0%";
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

    public void addPercentage(NutritionalInformationPercentage nutrPercentage){
        this.totalFat = addPercentageValue(totalFat, nutrPercentage.getTotalFat());
        this.saturatedFat = addPercentageValue(saturatedFat, nutrPercentage.getSaturatedFat());
        this.polyunsaturatedFat = addPercentageValue(polyunsaturatedFat, nutrPercentage.getPolyunsaturatedFat());
        this.monounsaturatedFat = addPercentageValue(monounsaturatedFat, nutrPercentage.getMonounsaturatedFat());
        this.transFatRegulation = addPercentageValue(transFatRegulation, nutrPercentage.getTransFatRegulation());
        this.cholesterol = addPercentageValue(cholesterol, nutrPercentage.getCholesterol());
        this.sodium = addPercentageValue(sodium, nutrPercentage.getSodium());
        this.potassium = addPercentageValue(potassium, nutrPercentage.getPotassium());
        this.totalCarbohydrates = addPercentageValue(totalCarbohydrates, nutrPercentage.getTotalCarbohydrates());
        this.dietaryFiber = addPercentageValue(dietaryFiber, nutrPercentage.getDietaryFiber());
        this.sugars = addPercentageValue(sugars, nutrPercentage.getSugars());
        this.protein = addPercentageValue(protein, nutrPercentage.getProtein());
        this.caffeine = addPercentageValue(caffeine, nutrPercentage.getCaffeine());
        this.vitaminA = addPercentageValue(vitaminA, nutrPercentage.getVitaminA());
        this.vitaminC = addPercentageValue(vitaminC, nutrPercentage.getVitaminC());
        this.calcium = addPercentageValue(calcium, nutrPercentage.getCalcium());
        this.iron = addPercentageValue(iron, nutrPercentage.getIron());
        this.vitaminD = addPercentageValue(vitaminD, nutrPercentage.getVitaminD());
        this.vitaminB6 = addPercentageValue(vitaminB6, nutrPercentage.getVitaminB6());
        this.cobalamin = addPercentageValue(cobalamin, nutrPercentage.getCobalamin());
        this.magnesium = addPercentageValue(magnesium, nutrPercentage.getMagnesium());
    }

    public void subtractPercentage(NutritionalInformationPercentage nutrPercentage){
        this.totalFat = subtractPercentageValue(totalFat, nutrPercentage.getTotalFat());
        this.saturatedFat = subtractPercentageValue(saturatedFat, nutrPercentage.getSaturatedFat());
        this.polyunsaturatedFat = subtractPercentageValue(polyunsaturatedFat, nutrPercentage.getPolyunsaturatedFat());
        this.monounsaturatedFat = subtractPercentageValue(monounsaturatedFat, nutrPercentage.getMonounsaturatedFat());
        this.transFatRegulation = subtractPercentageValue(transFatRegulation, nutrPercentage.getTransFatRegulation());
        this.cholesterol = subtractPercentageValue(cholesterol, nutrPercentage.getCholesterol());
        this.sodium = subtractPercentageValue(sodium, nutrPercentage.getSodium());
        this.potassium = subtractPercentageValue(potassium, nutrPercentage.getPotassium());
        this.totalCarbohydrates = subtractPercentageValue(totalCarbohydrates, nutrPercentage.getTotalCarbohydrates());
        this.dietaryFiber = subtractPercentageValue(dietaryFiber, nutrPercentage.getDietaryFiber());
        this.sugars = subtractPercentageValue(sugars, nutrPercentage.getSugars());
        this.protein = subtractPercentageValue(protein, nutrPercentage.getProtein());
        this.caffeine = subtractPercentageValue(caffeine, nutrPercentage.getCaffeine());
        this.vitaminA = subtractPercentageValue(vitaminA, nutrPercentage.getVitaminA());
        this.vitaminC = subtractPercentageValue(vitaminC, nutrPercentage.getVitaminC());
        this.calcium = subtractPercentageValue(calcium, nutrPercentage.getCalcium());
        this.iron = subtractPercentageValue(iron, nutrPercentage.getIron());
        this.vitaminD = subtractPercentageValue(vitaminD, nutrPercentage.getVitaminD());
        this.vitaminB6 = subtractPercentageValue(vitaminB6, nutrPercentage.getVitaminB6());
        this.cobalamin = subtractPercentageValue(cobalamin, nutrPercentage.getCobalamin());
        this.magnesium = subtractPercentageValue(magnesium, nutrPercentage.getMagnesium());
    }

    public int getInt(String perc){
        return Integer.parseInt(perc.split("%")[0]);
    }

    public String addPercentageValue(String perc1, String perc2){
        return (Integer.parseInt(perc1.split("%")[0]) + Integer.parseInt(perc2.split("%")[0])) + "%";
    }

    public String subtractPercentageValue(String perc1, String perc2){
        return (Integer.parseInt(perc1.split("%")[0]) - Integer.parseInt(perc2.split("%")[0])) + "%";
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
