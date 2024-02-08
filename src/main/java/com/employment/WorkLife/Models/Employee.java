package com.employment.WorkLife.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity

public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employee_id;

    private String first_name;
    private String last_name;

    private String email;
    private Integer phone_no;



    /*@JsonBackReference*/
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="department_id",nullable = false)

    private Departments departments;
    public void setDepartment(Departments department) {
        this.departments = department;
    }


    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="role_id",nullable = false)

    private RoleModel roleModel;
    public void setRole(RoleModel roleModel) {
        this.roleModel = roleModel;
    }


    /*@JsonManagedReference*/
    @OneToMany(mappedBy = "employee", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Address> addresses;


    @JsonManagedReference
    @OneToMany(mappedBy = "employee", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Salary> salaries;

}
