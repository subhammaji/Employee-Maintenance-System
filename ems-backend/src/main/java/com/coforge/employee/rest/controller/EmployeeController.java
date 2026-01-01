package com.coforge.employee.rest.controller;

import com.coforge.employee.dto.EmployeeDto;
import com.coforge.employee.mapper.EmployeeMapper;
import com.coforge.employee.rest.api.request.EmployeeCreate;
import com.coforge.employee.rest.api.request.EmployeeUpdate;
import com.coforge.employee.rest.constants.EmployeeConstants;
import com.coforge.employee.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping(EmployeeConstants.BASE_URL)
@Tag(name= "Employee Management System", description = "APIs for managing employees")
public class EmployeeController {

    private EmployeeService empService;
    private EmployeeMapper empMapper;

    //build create rest api
    @PostMapping
    @Operation(summary = "Create employee", description = "Create a new employee and return the created DTO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeCreate create) {
        EmployeeDto empDto = empService.createEmployee(empMapper.convertRequestToDto(create));
//        return ResponseEntity.ok(empDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(empDto);
    }

    //build get all employees rest api
    @GetMapping
    @Operation(summary = "Get all employees", description = "Retrieve list of all employees")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employees found"),
            @ApiResponse(responseCode = "404", description = "No employees found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<EmployeeDto>> findAllEmployees() {
        List<EmployeeDto> empDtoList = empService.retrieveAllEmployees();
        return ResponseEntity.ok(empDtoList);
    }

    //build get all employees rest api
    @GetMapping("/{id}")
    @Operation(summary = "Get employee by id", description = "Retrieve an employee by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee found"),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<EmployeeDto> findEmployeeById(@PathVariable Long id) {
        EmployeeDto empDto = empService.retrieveEmployee(id);
        return ResponseEntity.ok(empDto);
    }

    //build update employee api
    @PutMapping("/{id}")
    @Operation(summary = "Update employee", description = "Update an existing employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Employee updated successfully (no content)"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload"),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable("id") Long id, @RequestBody EmployeeUpdate update) throws Exception {
        EmployeeDto empDto = empService.updateEmployee(id, empMapper.convertRequestToDto(update));
        return new ResponseEntity<>(empDto, HttpStatus.NO_CONTENT);
    }

    //build delete employee api
    @Operation(summary = "Delete an Employee by ID", description = "Delete employee record corresponding to provided id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee Deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) throws Exception {
        empService.deleteEmployee(id);
        return ResponseEntity.status(200).body("Employee deleted successfully with id: "+ id);
    }

}

