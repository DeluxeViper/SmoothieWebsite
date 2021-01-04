package com.ceruleansource.SmoothieWebsite.backend.Models.user;

import com.ceruleansource.SmoothieWebsite.backend.Models.Smoothie;
import org.hibernate.annotations.Fetch;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * JPA Model (Entity) for User
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Length(min = 1, max = 32)
    private String firstName;

    @NotNull
    @Length(min = 1, max = 32)
    private String lastName;

    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    @Length(min = 8, max = 64)
    private String password;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Smoothie> favouriteSmoothies;

    // TODO: Double check the logic for this
    private String intake;
    private boolean active;
    private String roles;

    public User() {
    }

    public User(String firstName, String lastName, String email, String password, List<Smoothie> favouriteSmoothies, String intake, boolean active, String roles) {
        this.firstName = firstName;
        this.lastName = lastName;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", favouriteSmoothies=" + favouriteSmoothies +
                ", intake='" + intake + '\'' +
                ", active=" + active +
                ", roles='" + roles + '\'' +
                '}';
    }
}
