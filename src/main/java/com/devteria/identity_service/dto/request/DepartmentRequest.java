package com.devteria.identity_service.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentRequest {

    @NotBlank(message = "Department name is required")
    @Size(max = 255, message = "Department name must be less than or equal to 255 characters")
    private String depName;

    @NotBlank(message = "Department short name is required")
    @Size(max = 3, message = "Department short name must be less than or equal to 3 characters")
    private String depShortName;

    @NotNull(message = "Manager ID is required")
    private Long manageID;

}
