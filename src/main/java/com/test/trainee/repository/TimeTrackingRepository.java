package com.test.trainee.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.trainee.model.TimeTracking;

// Repository interface for TimeTracking entity.
@Repository
public interface TimeTrackingRepository extends JpaRepository<TimeTracking, Long> {

    // Method to find a TimeTracking entry by its id.
    Optional<TimeTracking> findById(final Long id);

    // Method to find all TimeTracking entries.
    List<TimeTracking> findAll();

    // Method to delete a TimeTracking entry by its id.
    void deleteById(final Long id);
}
