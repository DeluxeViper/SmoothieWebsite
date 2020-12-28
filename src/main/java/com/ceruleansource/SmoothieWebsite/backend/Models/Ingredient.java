package com.ceruleansource.SmoothieWebsite.backend.Models;

import javax.persistence.*;

@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String quantityType;

    private String quantityValue;

    @OneToOne
    private NutritionalInformation nutritionalInformation;

    public Ingredient(){
    }

    public Ingredient(Long id, String name, String quantityType, String quantityValue, NutritionalInformation nutritionalInformation) {
        this.id = id;
        this.name = name;
        this.quantityType = quantityType;
        this.quantityValue = quantityValue;
        this.nutritionalInformation = nutritionalInformation;
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

    public String getQuantityType() {
        return quantityType;
    }

    public void setQuantityType(String quantityType) {
        this.quantityType = quantityType;
    }

    public String getQuantityValue() {
        return quantityValue;
    }

    public void setQuantityValue(String quantityValue) {
        this.quantityValue = quantityValue;
    }

    public NutritionalInformation getNutritionalInformation() {
        return nutritionalInformation;
    }

    public void setNutritionalInformation(NutritionalInformation nutritionalInformation) {
        this.nutritionalInformation = nutritionalInformation;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantityType='" + quantityType + '\'' +
                ", quantityValue='" + quantityValue + '\'' +
                ", nutritionalInformation=" + nutritionalInformation +
                '}';
    }
}
