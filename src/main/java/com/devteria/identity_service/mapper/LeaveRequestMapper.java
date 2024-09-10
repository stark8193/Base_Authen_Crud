package com.devteria.identity_service.mapper;


import com.devteria.identity_service.dto.request.LeaveRequestRequest;
import com.devteria.identity_service.dto.response.LeaveRequestResponse;
import com.devteria.identity_service.entity.LeaveRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LeaveRequestMapper {
    LeaveRequest toLeaveRequest(LeaveRequestRequest request);

    default LeaveRequestResponse toLeaveRequestResponse(LeaveRequest leaveRequest) {
        if (leaveRequest == null) {
            return null;
        }

        return LeaveRequestResponse.builder()
                .startDate(leaveRequest.getStartDate())
                .endDate(leaveRequest.getEndDate())
                .status(leaveRequest.getStatus())
                .reason(leaveRequest.getReason())
                .employeeName(leaveRequest.getEmployee() != null ? leaveRequest.getEmployee().getEmployeeName() : null)
                .leaveTypeName(leaveRequest.getLeaveType() != null ? leaveRequest.getLeaveType().getLeaveTypeName() : null)
                .build();
    }
}
