package com.ceruleansource.SmoothieWebsite.backend.Models.user;

import com.ceruleansource.SmoothieWebsite.backend.Models.Smoothie;
import org.hibernate.annotations.Fetch;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * JPA Model (Entity) for User
 */
@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"email", "roles"})
})
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Smoothie> smoothies = new HashSet<>();

    // TODO: Double check the logic for this
    private String intake;
    private boolean active;
    private String roles;

    public User() {
    }

    public User(String firstName, String lastName, String email, String password, String intake, boolean active, String roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
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

    public Set<Smoothie> getSmoothies() {
        return smoothies;
    }

    public Set<String> getSmoothieNames(){
        Set<String> smoothieNames = new HashSet<>();
        smoothies.forEach(smoothie -> smoothieNames.add(smoothie.getName()));
        return smoothieNames;
    }

    public void setSmoothies(Set<Smoothie> smoothies) {
        this.smoothies.clear();
        if (smoothies != null){
            this.smoothies.addAll(smoothies);
        }
        System.out.println("Set smoothies: " + this.smoothies);
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
    @Transactional
    public String toString() {
        Set<String> setNames = new HashSet<>();
        smoothies.forEach(smoothie -> setNames.add(smoothie.getName()));
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", smoothieNames=" + setNames +
                ", intake='" + intake + '\'' +
                ", active=" + active +
                ", roles='" + roles + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
