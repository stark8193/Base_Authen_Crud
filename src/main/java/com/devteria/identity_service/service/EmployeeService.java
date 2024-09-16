package com.devteria.identity_service.service;

import com.devteria.identity_service.dto.request.EmployeeRequest;
import com.devteria.identity_service.dto.request.Recipient;
import com.devteria.identity_service.dto.request.SendEmailRequest;
import com.devteria.identity_service.dto.request.UserCreationRequest;
import com.devteria.identity_service.dto.response.EmployeeResponse;
import com.devteria.identity_service.dto.response.RoleResponse;
import com.devteria.identity_service.entity.Employee;
import com.devteria.identity_service.entity.Role;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<EmployeeResponse> getAllEmpWithPage(Integer page, Integer size, String sort){
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("employeeName").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("employeeName").descending();
        }
        Pageable pageable = PageRequest.of(page, size, sortable);
        return  employeeRepository.findAll(pageable).stream().map(employeeMapper::toEmployeeResponse).toList();
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
}
