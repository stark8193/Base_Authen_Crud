package com.devteria.identity_service.controller;

import com.devteria.identity_service.dto.request.ApiResponse;
import com.devteria.identity_service.dto.request.LeaveRequestRequest;
import com.devteria.identity_service.dto.request.TimeSheetRequest;
import com.devteria.identity_service.dto.response.TimeSheetResponse;
import com.devteria.identity_service.service.TimeSheetService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/time-sheets")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TimeSheetController {
    TimeSheetService timeSheetService;

    @PostMapping
    ApiResponse<TimeSheetResponse> createTimeSheet(@RequestBody @Valid TimeSheetRequest request) {
        return ApiResponse.<TimeSheetResponse>builder()
                .data(timeSheetService.createTimeSheet(request))
                .build();
    }

    @GetMapping
    ApiResponse<Map<String, Object>> getAllTimeSheetsWithPage(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort
    ) {

        Map<String, Object> data = timeSheetService.getAllTimeSheetsWithPage(page, size, sort);

        return ApiResponse.<Map<String, Object>>builder()
                .data(data)
                .build();
    }

    @PutMapping("/{timeSheetId}")
    ApiResponse<TimeSheetResponse> updateTimeSheet(@PathVariable String timeSheetId, @RequestBody TimeSheetRequest request) {
        return ApiResponse.<TimeSheetResponse>builder()
                .data(timeSheetService.updateTimeSheet(timeSheetId, request))
                .build();
    }

//    @GetMapping("/{userId}")
//    ApiResponse<TimeSheetResponse> getUser(@PathVariable("userId") String userId) {
//        return ApiResponse.<TimeSheetResponse>builder()
//                .data(userService.getUser(userId))
//                .build();
//    }
//
//    @DeleteMapping("/{userId}")
//    ApiResponse<String> deleteUser(@PathVariable String userId) {
//        userService.deleteUser(userId);
//        return ApiResponse.<String>builder().data("User has been deleted").build();
//    }
}
