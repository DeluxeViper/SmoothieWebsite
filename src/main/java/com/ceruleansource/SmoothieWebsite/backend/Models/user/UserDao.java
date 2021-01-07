package com.ceruleansource.SmoothieWebsite.backend.Models.user;

import com.ceruleansource.SmoothieWebsite.backend.Models.user.daos.Dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public class UserDao implements Dao<User> {

    private EntityManager entityManager;

    @Override
    public Optional<User> get(long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public List<User> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM User e");
        return null;
    }

    @Override
    public void save(User user) {
        executeInsideTransaction(entityManager -> entityManager.persist(user));
    }

    @Override
    public void update(User user, String[] params) {
        user.setFirstName(Objects.requireNonNull(params[0], "First Name cannot be null"));
        user.setEmail(Objects.requireNonNull(params[0], "Email cannot be null"));
        user.setLastName(Objects.requireNonNull(params[0], "Last Name cannot be null"));
    }

    @Override
    public void delete(User user) {

    }

    private void executeInsideTransaction(Consumer<EntityManager> action){
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            action.accept(entityManager);
            tx.commit();
        } catch (RuntimeException e){
            tx.rollback();
            throw e;
        }
    }
}
