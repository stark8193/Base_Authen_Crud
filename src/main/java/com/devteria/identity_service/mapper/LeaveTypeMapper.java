package com.devteria.identity_service.mapper;


import com.devteria.identity_service.dto.request.EmployeeRequest;
import com.devteria.identity_service.dto.request.LeaveTypeRequest;
import com.devteria.identity_service.dto.response.LeaveTypeResponse;
import com.devteria.identity_service.entity.Employee;
import com.devteria.identity_service.entity.LeaveType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LeaveTypeMapper {
    LeaveType toLeaveType(LeaveTypeRequest request);

    LeaveTypeResponse toLeaveTypeResponse(LeaveType leaveType);

    void updateLeaveType(@MappingTarget LeaveType leaveType, LeaveTypeRequest request);
}
