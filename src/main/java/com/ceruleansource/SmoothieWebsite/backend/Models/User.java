package com.ceruleansource.SmoothieWebsite.backend.Models;

import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private String password;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Smoothie> favouriteSmoothies;

    // TODO: Double check the logic for this
    private String intake;
    private boolean active;
    private String roles;

    protected User() {
    }

    public User(String name, String email, String password, List<Smoothie> favouriteSmoothies, String intake, boolean active, String roles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.favouriteSmoothies = favouriteSmoothies;
        this.intake = intake;
        this.active = active;
        this.roles = roles;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Smoothie> getFavouriteSmoothies() {
        return favouriteSmoothies;
    }

    public void setFavouriteSmoothies(List<Smoothie> favouriteSmoothies) {
        this.favouriteSmoothies = favouriteSmoothies;
    }

    public String getIntake() {
        return intake;
    }

    public void setIntake(String intake) {
        this.intake = intake;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", favouriteSmoothies=" + favouriteSmoothies +
                ", intake='" + intake + '\'' +
                ", active=" + active +
                ", roles='" + roles + '\'' +
                '}';
    }
}
