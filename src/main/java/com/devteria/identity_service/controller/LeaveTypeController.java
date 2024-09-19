package com.devteria.identity_service.controller;

import com.devteria.identity_service.dto.request.ApiResponse;
import com.devteria.identity_service.dto.request.LeaveTypeRequest;
import com.devteria.identity_service.dto.request.UserUpdateRequest;
import com.devteria.identity_service.dto.response.LeaveTypeResponse;
import com.devteria.identity_service.service.LeaveTypeService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/leave-type")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class LeaveTypeController {
    LeaveTypeService leaveTypeService;

    @PostMapping
    ApiResponse<LeaveTypeResponse> createLeaveType(@RequestBody @Valid LeaveTypeRequest request) {
        return ApiResponse.<LeaveTypeResponse>builder()
                .data(leaveTypeService.createLeaveType(request))
                .build();
    }

    @GetMapping
    ApiResponse<Map<String, Object>> getAllLeaveType(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort
    ) {
//        return ApiResponse.<List<LeaveTypeResponse>>builder()
//                .data(leaveTypeService.getAllLeaveType())
//                .build();
        Map<String, Object> data = leaveTypeService.getAllLeaveType(page, size, sort);
        return ApiResponse.<Map<String, Object>>builder()
                .data(data)
                .build();
    }

    @GetMapping("/{leaveTypeId}")
    ApiResponse<LeaveTypeResponse> getLeaveType(@PathVariable("leaveTypeId") String leaveTypeId) {
        return ApiResponse.<LeaveTypeResponse>builder()
                .data(leaveTypeService.getLeaveType(leaveTypeId))
                .build();
    }

    @DeleteMapping("/{leaveTypeId}")
    ApiResponse<String> deleteLeaveType(@PathVariable String leaveTypeId) {
        leaveTypeService.deleteLeaveType(leaveTypeId);
        return ApiResponse.<String>builder().data("leaveType has been deleted").build();
    }

    @PutMapping("/{leaveTypeId}")
    ApiResponse<LeaveTypeResponse> updateLeaveType(@PathVariable String leaveTypeId, @RequestBody LeaveTypeRequest request) {
        return ApiResponse.<LeaveTypeResponse>builder()
                .data(leaveTypeService.updateLeaveType(leaveTypeId, request))
                .build();
    }
}
