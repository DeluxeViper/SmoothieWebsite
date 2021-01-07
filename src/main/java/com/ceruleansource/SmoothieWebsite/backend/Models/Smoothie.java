package com.ceruleansource.SmoothieWebsite.backend.Models;

import com.ceruleansource.SmoothieWebsite.backend.Models.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Smoothie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name="smoothie_ingredients",
            joinColumns = @JoinColumn(name = "smoothie_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id", referencedColumnName = "id")
    )
    private List<Ingredient> ingredients = new ArrayList<>();

    protected Smoothie(){
    }

    public Smoothie(String name) {
        this.name = name;
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

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Smoothie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Smoothie smoothie = (Smoothie) o;
        return Objects.equals(id, smoothie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
