package com.employment.WorkLife.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity

public class Departments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer department_id;

    private String department_name;
    /*@JsonManagedReference*/
    @OneToMany(mappedBy = "departments", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Employee> employees;

    public Departments() {
        // Initialize the employees list in the constructor
        this.employees = new ArrayList<>();
    }
}
