package com.test.trainee.model;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Entity class representing the TimeTracking entity.
 * It maps to the "time_track_table" table in the database.
 * Provides getters and setters for accessing and modifying the properties of a
 * TimeTracking.
 */
@Data
@Entity
@Table(name = "time_track_table")
public class TimeTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Instant startDate;

    private Instant endDate;

    private String description;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task trackTask;

    public TimeTracking(final Instant endDate, final String description) {
        this.startDate = Instant.now();
        this.endDate = endDate;
        this.description = description;
    }

    public TimeTracking() {
        startDate = endDate = Instant.now();
    }

}
