package com.devteria.identity_service.service;

import com.devteria.identity_service.dto.request.LeaveTypeRequest;
import com.devteria.identity_service.dto.response.LeaveTypeResponse;
import com.devteria.identity_service.entity.LeaveType;
import com.devteria.identity_service.exception.AppException;
import com.devteria.identity_service.exception.ErrorCode;
import com.devteria.identity_service.mapper.LeaveTypeMapper;
import com.devteria.identity_service.repository.LeaveTypeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class LeaveTypeService {
    LeaveTypeRepository leaveTypeRepository;
    LeaveTypeMapper leaveTypeMapper;

    public LeaveTypeResponse createLeaveType(LeaveTypeRequest request){
        if(leaveTypeRepository.existsByLeaveTypeName((request.getLeaveTypeName()))){
            throw new AppException(ErrorCode.LEAVE_TYPE_NAME_EXISTED);
        }
        LeaveType leaveType = leaveTypeMapper.toLeaveType(request);
        leaveType = leaveTypeRepository.save(leaveType);
        return leaveTypeMapper.toLeaveTypeResponse(leaveType);
    }

    public List<LeaveTypeResponse> getAllLeaveType(){
        return leaveTypeRepository.findAll().stream().map(leaveTypeMapper::toLeaveTypeResponse).toList();
    }

    public LeaveTypeResponse getLeaveType(String id){
        LeaveType leaveType = leaveTypeRepository.findById(id).orElseThrow(
                ()->new AppException(ErrorCode.LEAVE_TYPE_NOT_EXISTED)
        );
        return leaveTypeMapper.toLeaveTypeResponse(leaveType);
    }

    public LeaveTypeResponse updateLeaveType(String id, LeaveTypeRequest request){
        LeaveType leaveType = leaveTypeRepository.findById(id).orElseThrow(
                ()->new AppException(ErrorCode.LEAVE_TYPE_NOT_EXISTED)
        );
        leaveTypeMapper.updateLeaveType(leaveType, request);
        LeaveType leaveTypeUpdate = leaveTypeRepository.save(leaveType);
        return leaveTypeMapper.toLeaveTypeResponse(leaveTypeUpdate);
    }

    public void deleteLeaveType(String id){
        leaveTypeRepository.findById(id).orElseThrow(
                ()->new AppException(ErrorCode.LEAVE_TYPE_NOT_EXISTED)
        );
        leaveTypeRepository.deleteById(id);
    }

    public boolean existedById(String id){
        return leaveTypeRepository.existsById(id);
    }

}
