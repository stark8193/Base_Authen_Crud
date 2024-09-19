package com.devteria.identity_service.controller;

import com.devteria.identity_service.dto.ApiQueryResponse;
import com.devteria.identity_service.service.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/query")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class QueryController {
    DepartmentService departmentService;
    EmployeeService employeeService;
    JobService jobService;
    LeaveTypeService leaveTypeService;

    RoleService roleService;


    @GetMapping("/department")
    List<ApiQueryResponse> queryDepart(){
        return departmentService.query();
    }

    @GetMapping("/employee")
    List<ApiQueryResponse> queryEmp(){
        return employeeService.query();
    }

    @GetMapping("/leaveType")
    List<ApiQueryResponse> queryLeaveType(){
        return leaveTypeService.query();
    }

    @GetMapping("/job")
    List<ApiQueryResponse> queryJob(){
        return jobService.query();
    }
    @GetMapping("/role")
    List<ApiQueryResponse> queryRole(){
        return roleService.query();
    }
}
