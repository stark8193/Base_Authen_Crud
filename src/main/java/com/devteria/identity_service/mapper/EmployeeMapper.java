package com.devteria.identity_service.mapper;

import com.devteria.identity_service.dto.request.EmployeeRequest;
import com.devteria.identity_service.dto.response.EmployeeResponse;
import com.devteria.identity_service.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    Employee toEmployee(EmployeeRequest request);

    EmployeeResponse toEmployeeResponse(Employee employee);

    void updateEmployee(@MappingTarget Employee employee, EmployeeRequest request);
}
