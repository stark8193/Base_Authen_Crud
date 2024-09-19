package com.devteria.identity_service.service;

import com.devteria.identity_service.dto.request.EmployeeRequest;
import com.devteria.identity_service.dto.request.Recipient;
import com.devteria.identity_service.dto.request.SendEmailRequest;
import com.devteria.identity_service.dto.request.UserCreationRequest;
import com.devteria.identity_service.dto.response.EmployeeResponse;
import com.devteria.identity_service.dto.response.RoleResponse;
import com.devteria.identity_service.entity.Employee;
import com.devteria.identity_service.entity.enumeration.UserStatus;
import com.devteria.identity_service.exception.AppException;
import com.devteria.identity_service.exception.ErrorCode;
import com.devteria.identity_service.mapper.EmployeeMapper;
import com.devteria.identity_service.mapper.RoleMapper;
import com.devteria.identity_service.repository.EmployeeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class EmployeeService {
    EmployeeRepository employeeRepository;
    EmployeeMapper employeeMapper;
    UserService userService;
    PasswordEncoder passwordEncoder;
    EmailService emailService;
    RoleService roleService;
    RoleMapper roleMapper;

    @NonFinal
    static final String TK_MK = "user2";

    public EmployeeResponse createEmployee(EmployeeRequest request) {
        Employee employee = employeeMapper.toEmployee(request);

        Employee savedEmployee = employeeRepository.save(employee);

        EmployeeResponse employeeResponse = employeeMapper.toEmployeeResponse(savedEmployee);
        RoleResponse role = roleService.getRole(request.getRole().getId());
        employeeResponse.setRole(roleMapper.toRole(role));

//        String randomUsername = RandomStringUtils.randomAlphanumeric(6);
//        String randomPassword = RandomStringUtils.randomAlphanumeric(6);

        UserCreationRequest user = UserCreationRequest.builder()
                .username(TK_MK)
                .password(passwordEncoder.encode(TK_MK))
                .employee(savedEmployee)
                .userStatus(UserStatus.ACTIVE)
                .build();
        userService.createUser(user);

        SendEmailRequest sendEmailRequest = SendEmailRequest.builder()
                .to(Recipient.builder()
                        .email(savedEmployee.getEmail())
                        .name(savedEmployee.getEmployeeName())
                        .build())
                .subject("Chào mừng bạn, day la tai khoan mat khau:")
                .htmlContent("<p>username: /password: </p>")
                .build();
        emailService.sendEmail(sendEmailRequest);

        return employeeResponse;
    }

    public List<EmployeeResponse> getAllEmp(){
        return employeeRepository.findAll().stream().map(employeeMapper::toEmployeeResponse).toList();
    }

    public Map<String, Object> getAllEmpWithPage(Integer page, Integer size, String sort) {
        Sort sortable = null;
        if (sort.equalsIgnoreCase("ASC")) {
            sortable = Sort.by("employeeName").ascending();
        } else if (sort.equalsIgnoreCase("DESC")) {
            sortable = Sort.by("employeeName").descending();
        }

        Pageable pageable = PageRequest.of(page, size, sortable);

        Page<Employee> employeePage = employeeRepository.findAll(pageable);

        List<EmployeeResponse> employees = employeePage.getContent()
                .stream()
                .map(employeeMapper::toEmployeeResponse)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("employees", employees);
        response.put("totalItems", employeePage.getTotalElements());
        response.put("totalPages", employeePage.getTotalPages());
        response.put("currentPage", employeePage.getNumber());        

        return response;
    }


    public EmployeeResponse getEmp(String id){
        return employeeMapper.toEmployeeResponse(employeeRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_FOUND)));
    }

    public EmployeeResponse updateEmp(String id, EmployeeRequest request){
        Employee employee = employeeRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.EMPLOYEE_NOT_FOUND));

        employeeMapper.updateEmployee(employee, request);

        Employee updatedEmployee = employeeRepository.save(employee);

        return employeeMapper.toEmployeeResponse(updatedEmployee);
    }

    public boolean existedById(String id){
        return employeeRepository.existsById(id);
    }

    public long empCount(String departId){
        return employeeRepository.countByDepartmentId(departId);
    }
}
