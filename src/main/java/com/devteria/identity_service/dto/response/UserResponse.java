package com.devteria.identity_service.dto.response;

import java.time.LocalDate;
import java.util.Set;

import com.devteria.identity_service.entity.Employee;
import com.devteria.identity_service.entity.enumeration.UserStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String username;
    UserStatus userStatus;
    Employee employee;
}
