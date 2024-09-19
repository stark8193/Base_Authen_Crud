package com.devteria.identity_service.dto.request;

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
public class EmployeeRequest {
    @NotNull
    String employeeName;

    @NotNull
    LocalDate birthDate;

    @NotNull
    Gender gender;

    @NotNull
    LocalDate hireDate;

    String email;

    @NotNull
    Long phone;

    @NotNull
    EmpStatus employeeStatus;

    @NotNull
    Long taxCode;

    @NotNull
    Long cccd;

    String address;

    Long bankAccountNumber;

    String bankName;

    String photoPath;

    Role role;

    Department department;

    Job job;
}
