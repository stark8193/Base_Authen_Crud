package com.devteria.identity_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentAndCountEmpResponse {
    String id;
    String depName;
    String depShortName;
    Long count;
}
