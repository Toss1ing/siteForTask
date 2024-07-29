package com.test.trainee.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.trainee.model.User;

// Repository interface for User entity.
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Method to find a User by its name.
    Optional<User> findByName(final String name);

    // Method to find a User by its id.
    Optional<User> findById(final Long id);

    // Method to find all User entities.
    List<User> findAll();
}
