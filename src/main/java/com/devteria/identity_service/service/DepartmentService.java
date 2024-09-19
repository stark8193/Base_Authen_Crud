package com.devteria.identity_service.service;
import com.devteria.identity_service.dto.ApiQueryResponse;
import com.devteria.identity_service.dto.request.DepartmentRequest;
import com.devteria.identity_service.dto.response.DepartmentAndCountEmpResponse;
import com.devteria.identity_service.dto.response.DepartmentResponse;
import com.devteria.identity_service.entity.Department;
import com.devteria.identity_service.exception.AppException;
import com.devteria.identity_service.exception.ErrorCode;
import com.devteria.identity_service.mapper.DepartmentMapper;
import com.devteria.identity_service.repository.DepartmentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DepartmentService {
    DepartmentRepository departmentRepository;
    DepartmentMapper departmentMapper;
    EmployeeService employeeService;

    public DepartmentResponse createDepartment(DepartmentRequest request) {
        Department department = departmentMapper.toDepartment(request);

        Department savedDepartment = departmentRepository.save(department);

        DepartmentResponse departmentResponse = departmentMapper.toDepartmentResponse(savedDepartment);

        return departmentResponse;
    }

    public Map<String, Object> getAllDepWithPage(Integer page, Integer size, String sort) {
        Sort sortable = null;
        if (sort.equalsIgnoreCase("ASC")) {
            sortable = Sort.by("depName").ascending();
        } else if (sort.equalsIgnoreCase("DESC")) {
            sortable = Sort.by("depName").descending();
        }

        Pageable pageable = PageRequest.of(page, size, sortable);

        Page<Department> depPage = departmentRepository.findAll(pageable);

        List<DepartmentAndCountEmpResponse> departments = depPage.getContent()
                .stream()
                .map(department -> DepartmentAndCountEmpResponse.builder()
                        .id(department.getId())
                        .depName(department.getDepName())
                        .depShortName(department.getDepShortName())
                        .count(employeeService.empCount(department.getId()))
                        .build())
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("departments", departments);
        response.put("totalItems", depPage.getTotalElements());
        response.put("totalPages", depPage.getTotalPages());
        response.put("currentPage", depPage.getNumber());


        return response;
    }

    public DepartmentResponse getDep(String id){
        return departmentMapper.toDepartmentResponse(departmentRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND)));
    }

    public DepartmentResponse updateDep(String id, DepartmentRequest request){
        Department department = departmentRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.NOT_FOUND));

        departmentMapper.updateDepartment(department, request);

        Department updatedEmployee = departmentRepository.save(department);

        return departmentMapper.toDepartmentResponse(updatedEmployee);
    }

    public void delete(String id) {
        departmentRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.NOT_FOUND));
        departmentRepository.deleteById(id);
    }

    public List<ApiQueryResponse> query(){
        List<ApiQueryResponse> list = departmentRepository.findAll().stream()
                .map(department -> ApiQueryResponse.builder()
                        .id(department.getId())
                        .value(department.getDepName())
                        .build())
                .toList();
        return list;
    }
}
