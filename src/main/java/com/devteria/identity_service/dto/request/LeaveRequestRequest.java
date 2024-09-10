package com.devteria.identity_service.dto.request;

import com.devteria.identity_service.entity.Employee;
import com.devteria.identity_service.entity.LeaveType;
import com.devteria.identity_service.entity.enumeration.LeaveRequestStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LeaveRequestRequest {
    @NotNull
    LocalDate startDate;

    @NotNull
    LocalDate endDate;

    LeaveRequestStatus status;

    String reason;

    Employee employee;

    LeaveType leaveType;
}
