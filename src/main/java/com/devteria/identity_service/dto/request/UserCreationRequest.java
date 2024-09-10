package com.devteria.identity_service.dto.request;

import java.time.LocalDate;

import com.devteria.identity_service.entity.Employee;
import com.devteria.identity_service.entity.enumeration.UserStatus;
import jakarta.validation.constraints.Size;

import com.devteria.identity_service.validator.DobConstraint;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 4, message = "USERNAME_INVALID")
    String username;

    @Size(min = 6, message = "INVALID_PASSWORD")
    String password;

    Employee employee;

    UserStatus userStatus;
}
