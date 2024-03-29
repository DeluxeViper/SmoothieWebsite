package com.ceruleansource.SmoothieWebsite.backend.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.text.DecimalFormat;

/**
 * Entity that describes ingredients nutrition facts in grams/milligrams
 *  - Follows the structure of Nutrition facts obtained from Google's search
 */
@Entity
public class NutritionalInformationGrams implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int calories;

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

    public NutritionalInformationGrams() {
        this.calories = 0;
        this.totalFat = "0 g";
        this.saturatedFat = "0 g";
        this.polyunsaturatedFat = "0 g";
        this.monounsaturatedFat = "0 g";
        this.transFatRegulation = "0 g";
        this.cholesterol = "0 mg";
        this.sodium = "0 mg";
        this.potassium = "0 mg";
        this.totalCarbohydrates = "0 g";
        this.dietaryFiber = "0 g";
        this.sugars = "0 g";
        this.protein = "0 g";
        this.caffeine = "0 g";

    }

    public NutritionalInformationGrams(Long id, int calories, String totalFat, String saturatedFat, String polyunsaturatedFat, String monounsaturatedFat, String transFatRegulation, String cholesterol, String sodium, String potassium, String totalCarbohydrates, String dietaryFiber, String sugars, String protein, String caffeine) {
        this.id = id;
        this.calories = calories;
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

    public void addGrams(NutritionalInformationGrams nutrGram) throws Exception {
        this.calories = calories + nutrGram.getCalories();
        this.totalFat = addGramValue(totalFat, nutrGram.getTotalFat());
        this.saturatedFat = addGramValue(saturatedFat, nutrGram.getSaturatedFat());
        this.polyunsaturatedFat = addGramValue(polyunsaturatedFat, nutrGram.getPolyunsaturatedFat());
        this.monounsaturatedFat = addGramValue(monounsaturatedFat, nutrGram.getMonounsaturatedFat());
        this.transFatRegulation = addGramValue(transFatRegulation, nutrGram.getTransFatRegulation());
        this.cholesterol = addGramValue(cholesterol, nutrGram.getCholesterol());
        this.sodium = addGramValue(sodium, nutrGram.getSodium());
        this.potassium = addGramValue(potassium, nutrGram.getPotassium());
        this.totalCarbohydrates = addGramValue(totalCarbohydrates, nutrGram.getTotalCarbohydrates());
        this.dietaryFiber = addGramValue(dietaryFiber, nutrGram.getDietaryFiber());
        this.sugars = addGramValue(sugars, nutrGram.getSugars());
        this.protein = addGramValue(protein, nutrGram.getProtein());
        this.caffeine = addGramValue(caffeine, nutrGram.getCaffeine());
    }

    public void subtractGrams(NutritionalInformationGrams nutrGram) throws Exception {
        this.calories = calories - nutrGram.getCalories();
        this.totalFat = subtractGramValue(totalFat, nutrGram.getTotalFat());
        this.saturatedFat = subtractGramValue(saturatedFat, nutrGram.getSaturatedFat());
        this.polyunsaturatedFat = subtractGramValue(polyunsaturatedFat, nutrGram.getPolyunsaturatedFat());
        this.monounsaturatedFat = subtractGramValue(monounsaturatedFat, nutrGram.getMonounsaturatedFat());
        this.transFatRegulation = subtractGramValue(transFatRegulation, nutrGram.getTransFatRegulation());
        this.cholesterol = subtractGramValue(cholesterol, nutrGram.getCholesterol());
        this.sodium = subtractGramValue(sodium, nutrGram.getSodium());
        this.potassium = subtractGramValue(potassium, nutrGram.getPotassium());
        this.totalCarbohydrates = subtractGramValue(totalCarbohydrates, nutrGram.getTotalCarbohydrates());
        this.dietaryFiber = subtractGramValue(dietaryFiber, nutrGram.getDietaryFiber());
        this.sugars = subtractGramValue(sugars, nutrGram.getSugars());
        this.protein = subtractGramValue(protein, nutrGram.getProtein());
        this.caffeine = subtractGramValue(caffeine, nutrGram.getCaffeine());
    }

    public NutritionalInformationGrams multiplyGrams(double multiplier) throws Exception {
        this.calories = (int) (calories *multiplier);
        this.totalFat = multiplyGramValue(totalFat, multiplier);
        this.saturatedFat = multiplyGramValue(saturatedFat, multiplier);
        this.polyunsaturatedFat = multiplyGramValue(saturatedFat, multiplier);
        this.monounsaturatedFat = multiplyGramValue(monounsaturatedFat, multiplier);
        this.transFatRegulation = multiplyGramValue(transFatRegulation, multiplier);
        this.cholesterol = multiplyGramValue(cholesterol, multiplier);
        this.sodium = multiplyGramValue(sodium, multiplier);
        this.potassium = multiplyGramValue(potassium, multiplier);
        this.totalCarbohydrates = multiplyGramValue(totalCarbohydrates, multiplier);
        this.dietaryFiber = multiplyGramValue(dietaryFiber, multiplier);
        this.sugars = multiplyGramValue(sugars, multiplier);
        this.protein = multiplyGramValue(protein, multiplier);
        this.caffeine = multiplyGramValue(caffeine, multiplier);
        return this;
    }

    // The following 3 methods are required due to the gram value being in a string format
    // ie. 5 mg
    private String multiplyGramValue(String gram, double multiplier) throws Exception{
        String [] gramSplit = gram.split(" ");
        DecimalFormat twoDecimalPlacesFormat = new DecimalFormat("#.##");
        double newDoubleValue = Double.parseDouble(gramSplit[0])*multiplier;
        if (gramSplit.length > 2){
            throw new Exception("Error, incorrect gram formatting for applying multiplier: " + gram);
        } else if (gramSplit.length == 1 && gramSplit[0].equals("0")){
            // Some protein values within the nutritional gram table don't have a unit, and start at 0
            //      so i'm adding a unit manually to prevent Array out of bounds.
            return Double.valueOf(twoDecimalPlacesFormat.format(newDoubleValue)) + " g";
        }
//        System.out.println("Multiplying: " + gram + " by " + multiplier + " = " + twoDecimalPlacesFormat.format(newDoubleValue) + " " + gramSplit[1]);
        return Double.valueOf(twoDecimalPlacesFormat.format(newDoubleValue)) + " " + gramSplit[1];
    }

    private String addGramValue(String gram1, String gram2) throws Exception {
//        System.out.println("Adding: " + gram1 + " and " + gram2);
        String [] gram1s = gram1.split(" ");
        String [] gram2s = gram2.split(" ");
        if (!gram1s[1].equals(gram2s[1])) {
            if (gram1s[1].equals("g") && gram2s[1].equals("mg")) {
                // Converting the second gram value (which is in mg) to g before adding the gram values
                return Math.round((Double.parseDouble(gram1s[0]) + Double.parseDouble(gram2s[0])/1000) * 100)/ 100.0 + " g";
            } else if (gram1s[1].equals("mg") && gram2s[1].equals("g")) {
                // Converting the first gram value (which is in mg) to g before adding the gram values
                return Math.round((Double.parseDouble(gram1s[0])/1000 + Double.parseDouble(gram2s[0])) * 100) / 100.0 + " g";
            } else {
                throw new Exception("Error adding gram values. Cannot compute. Not in equal measurements: " + gram1s[1] + " and " + gram2s[1]);
            }
        }
        return Math.round((Double.parseDouble(gram1s[0]) + Double.parseDouble(gram2s[0])) * 100)/ 100.0 + " " + gram1s[1];
    }

    private String subtractGramValue(String gram1, String gram2) throws Exception {
        String [] gram1s = gram1.split(" ");
        String [] gram2s = gram2.split(" ");
        if (!gram1s[1].equals(gram2s[1])) {
            if (gram1s[1].equals("g") && gram2s[1].equals("mg")) {
                // Converting the second gram value (which is in mg) to g before adding the gram values
                return Math.round((Double.parseDouble(gram1s[0]) - Double.parseDouble(gram2s[0])/1000) * 100)/ 100.0 + " g";
            } else if (gram1s[1].equals("mg") && gram2s[1].equals("g")) {
                // Converting the first gram value (which is in mg) to g before adding the gram values
                return Math.round((Double.parseDouble(gram1s[0])/1000 - Double.parseDouble(gram2s[0])) * 100) / 100.0 + " g";
            } else {
                throw new Exception("Error subtracting gram values. Cannot compute. Not in equal measurements: " + gram1s[1] + " and " + gram2s[1]);
            }
        }
        return Math.round((Double.parseDouble(gram1s[0]) - Double.parseDouble(gram2s[0])) * 100)/ 100.0 + " " + gram1s[1];
    }
    
    @Override
    public String toString() {
        return "NutritionalInformation{" +
                "id=" + id +
                ", calories=" + calories +
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
                '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
