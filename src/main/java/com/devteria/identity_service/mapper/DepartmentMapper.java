package com.devteria.identity_service.mapper;

import com.devteria.identity_service.dto.request.DepartmentRequest;
import com.devteria.identity_service.dto.request.EmployeeRequest;
import com.devteria.identity_service.dto.response.DepartmentResponse;
import com.devteria.identity_service.entity.Department;
import org.mapstruct.MappingTarget;

public interface DepartmentMapper {
    Department toDepartment(DepartmentRequest request);

    DepartmentResponse toDepartmentResponse(Department department);

    void updateDepartment(@MappingTarget Department department, DepartmentRequest request);
}
