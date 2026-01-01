package com.coforge.employee.domain;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "project")
    private String project;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "email", nullable = false, unique = true)
    private String email;
}
