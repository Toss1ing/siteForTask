package com.test.trainee.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Entity class representing the user entity.
 * It maps to the "user_tbl" table in the database.
 * Provides getters and setters for accessing and modifying the properties of a
 * user.
 */
@Table(name = "user_tbl")
@Entity
@Data
public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

        private String email;

        private String phoneNumber;

        private String password;

        private Instant registrationTime;

        @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
        @JoinTable(name = "user_task_tbl", joinColumns = {
                        @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
                                        @JoinColumn(name = "task_id", referencedColumnName = "id") })
        private List<Task> tasks;

        @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
        @JoinTable(name = "user_role_tbl", joinColumns = {
                        @JoinColumn(name = "user_id", referencedColumnName = "id")
        }, inverseJoinColumns = {
                        @JoinColumn(name = "role_id", referencedColumnName = "id")
        })
        private List<Role> userRole;

        public User(final String name, final String email, final String phoneNumber, final List<Task> tasks,
                        final String password, final List<Role> userRole) {
                this.name = name;
                this.email = email;
                this.phoneNumber = phoneNumber;
                this.tasks = tasks;
                this.password = password;
                this.registrationTime = Instant.now();
                this.userRole = userRole;
        }

        public User() {
                this.name = "";
                this.email = "";
                this.password = "";
                this.registrationTime = Instant.now();
                this.tasks = new ArrayList<>();
                this.userRole = new ArrayList<>();
        }

        public final void removeTask(final Long taskId) {
                Task task = this.tasks.stream().filter(t -> t.getId() == taskId).findFirst().orElse(null);
                if (task != null) {
                        this.tasks.remove(task);
                        task.getUsers().remove(this);
                }
        }

}
