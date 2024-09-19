package com.devteria.identity_service.controller;

import com.devteria.identity_service.dto.request.ApiResponse;
import com.devteria.identity_service.dto.request.DepartmentRequest;
import com.devteria.identity_service.dto.response.DepartmentResponse;
import com.devteria.identity_service.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DepartmentController {
    DepartmentService departmentService;
    @PostMapping
    ApiResponse<DepartmentResponse> createDep(@RequestBody @Valid DepartmentRequest request) {
        return ApiResponse.<DepartmentResponse>builder()
                .data(departmentService.createDepartment(request))
                .build();
    }

    @GetMapping
    ApiResponse<Map<String, Object>> getAllDepWithPage(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort
    ) {
        Map<String, Object> data = departmentService.getAllDepWithPage(page, size, sort);

        return ApiResponse.<Map<String, Object>>builder()
                .data(data)
                .build();
    }

    @GetMapping("/{depId}")
    ApiResponse<DepartmentResponse> getDep(@PathVariable("depId") String depId) {
        return ApiResponse.<DepartmentResponse>builder()
                .data(departmentService.getDep(depId))
                .build();
    }

    @PutMapping("/{depId}")
    ApiResponse<DepartmentResponse> updateDep(@PathVariable("depId") String depId, @RequestBody DepartmentRequest request) {
        return ApiResponse. <DepartmentResponse>builder()
                .data(departmentService.updateDep(depId, request))
                .build();
    }

    @DeleteMapping("/{depId}")
    ApiResponse<String> delete(@PathVariable String depId) {
        departmentService.delete(depId);
        return ApiResponse.<String>builder().data("Depart has been deleted").build();
    }
}
