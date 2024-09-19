package com.devteria.identity_service.dto.response;

import com.devteria.identity_service.entity.Department;
import com.devteria.identity_service.entity.Job;
import com.devteria.identity_service.entity.Role;
import com.devteria.identity_service.entity.enumeration.EmpStatus;
import com.devteria.identity_service.entity.enumeration.Gender;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeResponse {
    String id;
    String employeeName;
    LocalDate birthDate;
    Gender gender;
    LocalDate hireDate;
    String email;
    Long phone;
    EmpStatus employeeStatus;
    Long taxCode;
    Long cccd;
    String address;
    Long bankAccountNumber;
    String bankName;
    String photoPath;
    Role role;
    Department department;
    Job job;
}
