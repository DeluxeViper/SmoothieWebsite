package com.ceruleansource.SmoothieWebsite.backend.Repositories;

import com.ceruleansource.SmoothieWebsite.backend.Models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmailAndRoles(String email, String roles);
}
