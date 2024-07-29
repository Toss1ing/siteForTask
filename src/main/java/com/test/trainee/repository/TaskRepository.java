package com.test.trainee.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.trainee.model.Task;

// Repository interface for Task entity.
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Method to find a Task by its id.
    Optional<Task> findById(final Long id);

    // Method to find a Task by its name.
    Optional<Task> findByName(final String name);

    // Method to find all Task entities.
    List<Task> findAll();

    // Method to delete a Task by its id.
    void deleteById(final Long id);
}
