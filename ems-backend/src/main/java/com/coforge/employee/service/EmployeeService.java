package com.coforge.employee.service;

import com.coforge.employee.domain.Employee;
import com.coforge.employee.dto.EmployeeDto;
import com.coforge.employee.exceptions.EmployeeExceptionHandler;
import com.coforge.employee.mapper.EmployeeMapper;
import com.coforge.employee.repo.EmployeeRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor             // Dependency Injection through Constructor
@Slf4j
public class EmployeeService {

    private final EmployeeRepo empRepo;
    private final EmployeeMapper empMapper;

    @Caching
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        if(Objects.isNull(employeeDto)) {
            throw new EmployeeExceptionHandler("Please provide the payload of employee information");
        }
        Employee empEntity = this.empRepo.save(empMapper.toDomain(employeeDto));
        log.info("Employee created with ID: " + empEntity.getId());
        return empMapper.toDto(empEntity);
    }

    public EmployeeDto retrieveEmployee(Long id) {
        if(id == null || id <= 0) {
            throw new EmployeeExceptionHandler("Provide valid employee ID!");
        }
        Optional<Employee> empEntity = this.empRepo.findById(id);
        if(empEntity.isEmpty()) {
            throw new EmployeeExceptionHandler("Employee not found for ID: " + id);
        }
        Employee emp = empEntity.get();
        log.info("Employee retrieved with ID: " + id);
        return empMapper.toDto(emp);
    }

    @Caching
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) throws Exception {
        try {
            if(employeeDto == null || id == null) {
                throw new EmployeeExceptionHandler("Employee Id or Object must not be null!");
            }
            Optional<Employee> empEntity = this.empRepo.findById(id);
            if(empEntity.isPresent())
            {
                Employee emp = empEntity.get();
                if(employeeDto.getId() == null) {
                    employeeDto.setId(emp.getId());
                }
                empMapper.updateEmployee(emp, employeeDto);
                this.empRepo.save(emp);
            }
            log.info("Employee updated with ID: " + id);
            return empMapper.toDto(empRepo.getById(id));
        } catch(Exception e) {
            log.error("Error updating employee: " + e.getMessage());
            throw new Exception(e);
        }
    }

    public void deleteEmployee(Long id) throws Exception {
        try{
            if(id <= 0) {
                throw new EmployeeExceptionHandler("Invalid Employee Id: " + id);
            }
            else if(id == null) {
                throw new EmployeeExceptionHandler("Employee not found for Id: " + id);
            }
            this.empRepo.deleteById(id);
        } catch(Exception e) {
            log.error("Error deleting employee: " + e.getMessage());
            throw new Exception(e);
        }
    }

    public List<EmployeeDto> retrieveAllEmployees() {
        List<Employee> empEntityList = this.empRepo.findAll();
        if(CollectionUtils.isEmpty(empEntityList)) {
            throw new EmployeeExceptionHandler("No employees found in the database.");
        }
        log.info("Retrieved all employees. Count: " + (CollectionUtils.isEmpty(empEntityList) ? 0 : empEntityList.size()));
        return empEntityList.stream()
                .map(empMapper::toDto)
                .collect(Collectors.toList());
    }


}


