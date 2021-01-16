package com.ceruleansource.SmoothieWebsite.backend.Models;

import com.ceruleansource.SmoothieWebsite.backend.Models.user.User;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"name", "user_id"})
})
public class Smoothie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "smoothie_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private User user;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name="smoothie_ingredients",
            joinColumns = @JoinColumn(name = "smoothie_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private Set<Ingredient> ingredients = new HashSet<>();

    public Smoothie(){
    }

    public Smoothie(String name, User user) {
        this.name = name;
        this.user = user;
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

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
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
