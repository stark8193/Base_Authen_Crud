package com.devteria.identity_service.service;
import com.devteria.identity_service.dto.request.DepartmentRequest;
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

        List<DepartmentResponse> departments = depPage.getContent()
                .stream()
                .map(departmentMapper::toDepartmentResponse)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("employees", departments);
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
    public void delete(String dep) {
        departmentRepository.deleteById(dep);
    }

}
