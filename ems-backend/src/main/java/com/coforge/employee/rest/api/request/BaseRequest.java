package com.coforge.employee.rest.api.request;

import lombok.Data;

@Data
public class BaseRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private String project;
    private Double salary;
    private String email;
}
