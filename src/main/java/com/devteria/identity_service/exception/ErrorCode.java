package com.devteria.identity_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    ROLE_EXISTED(1009, "Role existed", HttpStatus.BAD_REQUEST),
    ROLE_NOT_EXISTED(1011, "Role not existed", HttpStatus.BAD_REQUEST),
    EMPLOYEE_NOT_FOUND(1010, "Employee not existed", HttpStatus.BAD_REQUEST),
    NOT_FOUND(1010, "Data not existed", HttpStatus.BAD_REQUEST),
    CANNOT_SEND_EMAIL(1012, "CANNOT_SEND_EMAIL", HttpStatus.BAD_REQUEST),
    LEAVE_TYPE_NAME_EXISTED(1013, "LEAVE_TYPE_NAME_EXISTED", HttpStatus.BAD_REQUEST),
    LEAVE_TYPE_NOT_EXISTED(1014, "LEAVE_TYPE_NOT_EXISTED", HttpStatus.BAD_REQUEST),
    CANNOT_CREATE_LEAVE_REQUEST(1015, "CANNOT_CREATE_LEAVE_REQUEST", HttpStatus.BAD_REQUEST),
    LEAVE_REQUEST_NOT_EXISTED(1016, "LEAVE_REQUEST_NOT_EXISTED", HttpStatus.BAD_REQUEST),
    ACCOUNT_EXPIRED(1017, "tài khoản hết hiệu lực", HttpStatus.BAD_REQUEST),
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
