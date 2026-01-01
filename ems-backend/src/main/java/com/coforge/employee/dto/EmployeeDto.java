package com.coforge.employee.dto;

import lombok.*;

@Data
public class EmployeeDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String project;
    private Double salary;
    private String email;
}
