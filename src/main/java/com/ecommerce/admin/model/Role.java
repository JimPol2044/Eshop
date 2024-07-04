package com.ecommerce.admin.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Setter
@Getter
@Entity
public class Role {
    @Id
    private String name;

    // Constructors, getters, and setters
    public Role() {}

    public Role(String name) {
        this.name = name;
    }

}
