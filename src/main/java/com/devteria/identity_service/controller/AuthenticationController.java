package com.devteria.identity_service.controller;

import java.text.ParseException;

import com.devteria.identity_service.entity.User;
import com.devteria.identity_service.service.EmployeeService;
import com.devteria.identity_service.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devteria.identity_service.dto.request.*;
import com.devteria.identity_service.dto.response.AuthenticationResponse;
import com.devteria.identity_service.dto.response.IntrospectResponse;
import com.devteria.identity_service.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;
    UserService userService;
    EmployeeService employeeService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var token = authenticationService.authenticate(request);
        var user = userService.getUserByUsername(request);
        var employee = employeeService.getEmp(user.getEmployee().getId());

        return ApiResponse.<AuthenticationResponse>builder().data(
                AuthenticationResponse.builder()
                        .employee(employee)
                        .token(token.getToken())
                        .authenticated(true)
                        .build()
        ).build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var data = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder().data(data).build();
    }

    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody RefreshRequest request)
            throws ParseException, JOSEException {
        var data = authenticationService.refreshToken(request);
        return ApiResponse.<AuthenticationResponse>builder().data(data).build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder().build();
    }
}
