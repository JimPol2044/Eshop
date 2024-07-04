package com.ecommerce.admin.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Customer {
    // Getters and setters
    private Long id;
    private String name;
    private String email;
    private String phone;

    // Constructors
    public Customer() {
    }

    public Customer(Long id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

}
