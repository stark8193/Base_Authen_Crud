package com.devteria.identity_service.controller;

import com.devteria.identity_service.dto.request.ApiResponse;
import com.devteria.identity_service.dto.request.TimeSheetRequest;
import com.devteria.identity_service.dto.response.TimeSheetResponse;
import com.devteria.identity_service.service.AuthenticationService;
import com.devteria.identity_service.service.TimeSheetService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Map;

@RestController
@RequestMapping("/time-sheets")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TimeSheetController {
    TimeSheetService timeSheetService;
    AuthenticationService authenticationService;

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

            data = timeSheetService.getAllTimeSheetsWithPageByEmpl(page, size, sort, employeeId);
        } else {
            data = timeSheetService.getAllTimeSheetsWithPage(page, size, sort);
        }

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
