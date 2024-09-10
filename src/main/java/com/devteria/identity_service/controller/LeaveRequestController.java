package com.devteria.identity_service.controller;

import com.devteria.identity_service.dto.request.ApiResponse;
import com.devteria.identity_service.dto.request.LeaveRequestRequest;
import com.devteria.identity_service.dto.response.LeaveRequestResponse;
import com.devteria.identity_service.service.LeaveRequestService;
import com.devteria.identity_service.service.LeaveTypeService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leave-request")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class LeaveRequestController {
    LeaveRequestService leaveRequestService;

    @PostMapping
    ApiResponse<LeaveRequestResponse> createLeaveRequest(@RequestBody @Valid LeaveRequestRequest request) {
        return ApiResponse.<LeaveRequestResponse>builder()
                .data(leaveRequestService.createLeaveRequest(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<LeaveRequestResponse>> getAllLeaveRequest() {
        return ApiResponse.<List<LeaveRequestResponse>>builder()
                .data(leaveRequestService.getAllLeaveRequest())
                .build();
    }

    @GetMapping("/{leaveTypeId}")
    ApiResponse<LeaveRequestResponse> getLeaveRequest(@PathVariable("leaveTypeId") String leaveTypeId) {
        return ApiResponse.<LeaveRequestResponse>builder()
                .data(leaveRequestService.getLeaveRequest(leaveTypeId))
                .build();
    }

    @DeleteMapping("/{leaveTypeId}")
    ApiResponse<String> deleteLeaveRequest(@PathVariable String leaveTypeId) {
        leaveRequestService.deleteLeaveRequest(leaveTypeId);
        return ApiResponse.<String>builder().data("leaveRequest has been deleted").build();
    }

    @PutMapping("/{leaveTypeId}")
    ApiResponse<LeaveRequestResponse> updateLeaveRequest(@PathVariable String leaveTypeId, @RequestBody LeaveRequestRequest request) {
        return ApiResponse.<LeaveRequestResponse>builder()
                .data(leaveRequestService.updateLeaveRequest(leaveTypeId, request))
                .build();
    }
}
