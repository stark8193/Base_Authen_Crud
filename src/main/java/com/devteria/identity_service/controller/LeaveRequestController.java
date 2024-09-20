package com.devteria.identity_service.controller;

import com.devteria.identity_service.dto.request.ApiResponse;
import com.devteria.identity_service.dto.request.LeaveRequestRequest;
import com.devteria.identity_service.dto.response.LeaveRequestResponse;
import com.devteria.identity_service.service.AuthenticationService;
import com.devteria.identity_service.service.LeaveRequestService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Map;

@RestController
@RequestMapping("/leave-request")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class LeaveRequestController {
    LeaveRequestService leaveRequestService;
    AuthenticationService authenticationService;

    @PostMapping
    ApiResponse<LeaveRequestResponse> createLeaveRequest(@RequestBody @Valid LeaveRequestRequest request) {
        return ApiResponse.<LeaveRequestResponse>builder()
                .data(leaveRequestService.createLeaveRequest(request))
                .build();
    }


//    @PreAuthorize("hasRole('ADMIN') || hasRole('HRM')")
    @GetMapping
    ApiResponse<Map<String, Object>> getAllLeaveRequest(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort,
            @RequestHeader("Authorization") String tokenHeader
    ) throws ParseException {
        String token = tokenHeader.startsWith("Bearer ") ? tokenHeader.substring(7) : tokenHeader;
        String employeeId = authenticationService.getEmpName(token);

        var authen = SecurityContextHolder.getContext().getAuthentication();
        log.info("USERNAME: {}",authen.getName());
        authen.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        Map<String, Object> data;
        if (authen.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER"))) {

            data = leaveRequestService.getAllLeaveRequestByEmpId(page, size, sort, employeeId);
        } else {
            data = leaveRequestService.getAllLeaveRequest(page, size, sort);
        }

        return ApiResponse.<Map<String, Object>>builder()
                .data(data)
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
