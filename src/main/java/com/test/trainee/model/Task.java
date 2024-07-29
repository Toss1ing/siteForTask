package com.test.trainee.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Entity class representing the Task entity.
 * It maps to the "task_tbl" table in the database.
 * Provides getters and setters for accessing and modifying the properties of a
 * Task.
 */
@Entity
@Data
@Table(name = "task_tbl")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String description;

    @JsonIgnore
    @ManyToMany(mappedBy = "tasks")
    private List<User> users;

    @JsonIgnore
    @OneToMany(mappedBy = "trackTask", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TimeTracking> taskTimeTracking;

    public Task(final String taskName, final String description, final List<User> users,
            final List<TimeTracking> taskTimeTrackings) {
        this.name = taskName;
        this.description = description;
        this.users = users;
        this.taskTimeTracking = taskTimeTrackings;
    }

    public Task() {
        this.name = "";
        this.description = "";
        this.users = new ArrayList<>();
        this.taskTimeTracking = new ArrayList<>();
    }

    public final void removeTimeTracking(final Long timeTrackingId) {
        TimeTracking timeTracking = this.taskTimeTracking.stream().filter(t -> t.getId() == timeTrackingId).findFirst()
                .orElse(null);
        if (timeTracking != null) {
            this.taskTimeTracking.remove(timeTracking);
        }
    }
}
