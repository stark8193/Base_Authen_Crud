package com.devteria.identity_service.service;

import com.devteria.identity_service.dto.request.LeaveRequestRequest;
import com.devteria.identity_service.dto.response.LeaveRequestResponse;
import com.devteria.identity_service.entity.LeaveRequest;
import com.devteria.identity_service.exception.AppException;
import com.devteria.identity_service.exception.ErrorCode;
import com.devteria.identity_service.mapper.LeaveRequestMapper;
import com.devteria.identity_service.repository.LeaveRequestRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class LeaveRequestService {
    LeaveRequestRepository leaveRequestRepository;
    LeaveRequestMapper leaveRequestMapper;
    EmployeeService employeeService;
    LeaveTypeService leaveTypeService;

    public LeaveRequestResponse createLeaveRequest(LeaveRequestRequest request){
        if (!leaveTypeService.existedById(request.getLeaveType().getId())
                || !employeeService.existedById(request.getEmployee().getId()))
        {
            throw new AppException(ErrorCode.CANNOT_CREATE_LEAVE_REQUEST);
        }
        LeaveRequest leaveRequest = leaveRequestMapper.toLeaveRequest(request);
        leaveRequest = leaveRequestRepository.save(leaveRequest);
        return  leaveRequestMapper.toLeaveRequestResponse(leaveRequest);
    }

    public Map<String, Object> getAllLeaveRequest(Integer page, Integer size, String sort){
        Sort sortable = null;
        if (sort.equalsIgnoreCase("ASC")) {
            sortable = Sort.by("endDate").ascending();
        } else if (sort.equalsIgnoreCase("DESC")) {
            sortable = Sort.by("endDate").descending();
        }

        Pageable pageable = PageRequest.of(page, size, sortable);

        Page<LeaveRequest> leaveRequestPage = leaveRequestRepository.findAll(pageable);

        List<LeaveRequestResponse> leaveRequests = leaveRequestPage.getContent()
                .stream()
                .map(leaveRequestMapper::toLeaveRequestResponse)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("data", leaveRequests);
        response.put("totalItems", leaveRequestPage.getTotalElements());
        response.put("totalPages", leaveRequestPage.getTotalPages());
        response.put("currentPage", leaveRequestPage.getNumber());

        return response;
    }

    public Map<String, Object> getAllLeaveRequestByEmpId(Integer page, Integer size, String sort, String employeeId){
        Sort sortable = null;
        if (sort.equalsIgnoreCase("ASC")) {
            sortable = Sort.by("endDate").ascending();
        } else if (sort.equalsIgnoreCase("DESC")) {
            sortable = Sort.by("endDate").descending();
        }

        Pageable pageable = PageRequest.of(page, size, sortable);

        Page<LeaveRequest> leaveRequestPage = leaveRequestRepository.findAllByEmployeeId(employeeId,pageable);

        List<LeaveRequestResponse> leaveRequests = leaveRequestPage.getContent()
                .stream()
                .map(leaveRequestMapper::toLeaveRequestResponse)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("data", leaveRequests);
        response.put("totalItems", leaveRequestPage.getTotalElements());
        response.put("totalPages", leaveRequestPage.getTotalPages());
        response.put("currentPage", leaveRequestPage.getNumber());

        return response;
    }

    public LeaveRequestResponse getLeaveRequest(String id){
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.LEAVE_REQUEST_NOT_EXISTED)
        );
        return leaveRequestMapper.toLeaveRequestResponse(leaveRequest);
    }

    public void deleteLeaveRequest(String id){
        leaveRequestRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.LEAVE_REQUEST_NOT_EXISTED)
        );
        leaveRequestRepository.deleteById(id);
    }

    public LeaveRequestResponse updateLeaveRequest(String id, LeaveRequestRequest request){
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.LEAVE_REQUEST_NOT_EXISTED)
        );
        leaveRequestMapper.updateLeaveRequest(leaveRequest, request);
        leaveRequest = leaveRequestRepository.save(leaveRequest);
        return leaveRequestMapper.toLeaveRequestResponse(leaveRequest);
    }

}
