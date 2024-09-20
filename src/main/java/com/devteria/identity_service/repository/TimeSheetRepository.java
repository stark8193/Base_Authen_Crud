package com.devteria.identity_service.repository;

import com.devteria.identity_service.entity.TimeSheet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeSheetRepository extends JpaRepository<TimeSheet, String> {
    Page<TimeSheet> findAllByEmployeeId(String employeeId, Pageable pageable);
}
