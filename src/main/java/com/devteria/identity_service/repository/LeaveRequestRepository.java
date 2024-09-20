package com.devteria.identity_service.repository;

import com.devteria.identity_service.entity.LeaveRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, String> {
    Page<LeaveRequest> findAllByEmployeeId(String employeeId, Pageable pageable);
}
