package com.test.trainee.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.trainee.model.Role;

// Repository interface for Role entity.
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    // Method to find a Role by its id.
    Optional<Role> findById(final Long id);

    // Method to find all Role entities.
    List<Role> findAll();

    // Method to delete a Role by its id.
    void deleteById(final Long id);

    // Method to find a Role by its name.
    Optional<Role> findByName(final String roleName);
}
