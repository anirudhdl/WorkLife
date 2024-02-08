package com.employment.WorkLife.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class RoleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer role_id;
    private String role_name;

    @JsonManagedReference
    @OneToMany(mappedBy = "roleModel", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Employee> employees;

    public RoleModel() {
        // Initialize the employees list in the constructor
        this.employees = new ArrayList<>();
    }
}
