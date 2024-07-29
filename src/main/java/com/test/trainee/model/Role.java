package com.test.trainee.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Entity class representing the Role entity.
 * It maps to the "role_tbl" table in the database.
 * Provides getters and setters for accessing and modifying the properties of a
 * Role.
 */
@Data
@Entity
@Table(name = "role_tbl")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "userRole")
    public List<User> users;

    public Role(final String name, List<User> users) {
        this.name = name;
        this.users = users;
    }

    public Role() {
        this.name = "";
        this.users = new ArrayList<>();
    }

}
