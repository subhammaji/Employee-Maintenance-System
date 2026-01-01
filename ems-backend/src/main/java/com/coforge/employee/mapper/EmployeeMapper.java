package com.coforge.employee.mapper;

import com.coforge.employee.domain.Employee;
import com.coforge.employee.dto.EmployeeDto;
import com.coforge.employee.rest.api.request.BaseRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeDto toDto(Employee employee);

    Employee toDomain(EmployeeDto employeeDto);

    void updateEmployee(@MappingTarget Employee target, EmployeeDto source);

    EmployeeDto convertRequestToDto(BaseRequest empRequest);

}
