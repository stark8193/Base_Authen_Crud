package com.devteria.identity_service.controller;

import com.devteria.identity_service.dto.request.ApiResponse;
import com.devteria.identity_service.dto.request.EmployeeRequest;
import com.devteria.identity_service.dto.response.EmployeeResponse;
import com.devteria.identity_service.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class EmployeeController {
    EmployeeService employeeService;

    @PostMapping
    ApiResponse<EmployeeResponse> createEmp(@RequestBody @Valid EmployeeRequest request) {
        return ApiResponse.<EmployeeResponse>builder()
                .data(employeeService.createEmployee(request))
                .build();
    }

//    @GetMapping
//    ApiResponse<List<EmployeeResponse>> getAllEmp() {
//        var authen = SecurityContextHolder.getContext().getAuthentication();
//        log.info("USERNAME: {}",authen.getName());
//        authen.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
//
//        return ApiResponse.<List<EmployeeResponse>>builder()
//                .data(employeeService.getAllEmp())
//                .build();
//    }

    @GetMapping
    ApiResponse<List<EmployeeResponse>> getAllEmpWithPage(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort
    ) {

        return ApiResponse.<List<EmployeeResponse>>builder()
                .data(employeeService.getAllEmpWithPage(page, size, sort))
                .build();
    }

    @GetMapping("/{empId}")
    ApiResponse<EmployeeResponse> getEmp(@PathVariable("empId") String empId) {
        return ApiResponse.<EmployeeResponse>builder()
                .data(employeeService.getEmp(empId))
                .build();
    }

    @PutMapping("/{empId}")
    ApiResponse<EmployeeResponse> updateEmp(@PathVariable("empId") String empId, @RequestBody EmployeeRequest request) {
        return ApiResponse.<EmployeeResponse>builder()
                .data(employeeService.updateEmp(empId, request))
                .build();
    }
}
