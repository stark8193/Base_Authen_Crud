package com.devteria.identity_service.repository;

import com.devteria.identity_service.entity.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveTypeRepository extends JpaRepository<LeaveType, String> {
    boolean existsByLeaveTypeName(String leaveTypeName);
}
