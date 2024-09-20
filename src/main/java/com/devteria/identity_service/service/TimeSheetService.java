package com.devteria.identity_service.service;

import com.devteria.identity_service.dto.request.TimeSheetRequest;
import com.devteria.identity_service.dto.response.TimeSheetResponse;
import com.devteria.identity_service.entity.TimeSheet;
import com.devteria.identity_service.mapper.TimeSheetMapper;
import com.devteria.identity_service.repository.TimeSheetRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TimeSheetService {
    TimeSheetRepository timeSheetRepository;
    TimeSheetMapper timeSheetMapper;

    public TimeSheetResponse createTimeSheet(TimeSheetRequest request){
        TimeSheet timeSheet = timeSheetMapper.toTimeSheet(request);

        // Đặt múi giờ thành Việt Nam (UTC+7)
        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        ZonedDateTime timeInZoned = ZonedDateTime.now(zoneId);

        // Chuyển đổi ZonedDateTime thành LocalDateTime để lưu
        LocalDateTime timeIn = timeInZoned.toLocalDateTime();
        timeSheet.setTimeIn(timeIn);

        // Đặt timeOut là 00:00:00 cùng ngày với timeIn và chuyển thành LocalDateTime
        LocalDate dateIn = timeIn.toLocalDate();
        LocalDateTime timeOut = LocalDateTime.of(dateIn, LocalTime.MIDNIGHT);
        timeSheet.setTimeOut(timeOut);

        timeSheet.setLate(0L);
        timeSheet.setWorkHour(0L);

        timeSheet = timeSheetRepository.save(timeSheet);
        return timeSheetMapper.toTimeSheetResponse(timeSheet);
    }

    public Map<String, Object> getAllTimeSheetsWithPage(Integer page, Integer size, String sort) {
        Sort sortable = null;
        if (sort.equalsIgnoreCase("ASC")) {
            sortable = Sort.by("timeIn").ascending();
        } else if (sort.equalsIgnoreCase("DESC")) {
            sortable = Sort.by("timeIn").descending();
        }

        Pageable pageable = PageRequest.of(page, size, sortable);

        Page<TimeSheet> timeSheetPage = timeSheetRepository.findAll(pageable);

        List<TimeSheetResponse> timeSheets = timeSheetPage.getContent()
                .stream()
                .map(timeSheetMapper::toTimeSheetResponse)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("data", timeSheets);
        response.put("totalItems", timeSheetPage.getTotalElements());
        response.put("totalPages", timeSheetPage.getTotalPages());
        response.put("currentPage", timeSheetPage.getNumber());

        return response;
    }

    public Map<String, Object> getAllTimeSheetsWithPageByEmpl(Integer page, Integer size, String sort, String employeeId) {
        Sort sortable;
        if (sort.equalsIgnoreCase("ASC")) {
            sortable = Sort.by("timeIn").ascending();
        } else if (sort.equalsIgnoreCase("DESC")) {
            sortable = Sort.by("timeIn").descending();
        } else {
            throw new IllegalArgumentException("Invalid sort order: " + sort);
        }

        Pageable pageable = PageRequest.of(page, size, sortable);

        // Fetch TimeSheet by employeeId with pagination
        Page<TimeSheet> timeSheetPage = timeSheetRepository.findAllByEmployeeId(employeeId, pageable);

        List<TimeSheetResponse> timeSheets = timeSheetPage.getContent()
                .stream()
                .map(timeSheetMapper::toTimeSheetResponse)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("data", timeSheets);
        response.put("totalItems", timeSheetPage.getTotalElements());
        response.put("totalPages", timeSheetPage.getTotalPages());
        response.put("currentPage", timeSheetPage.getNumber());

        return response;
    }


    public TimeSheetResponse updateTimeSheet(String id, TimeSheetRequest request){
        TimeSheet timeSheet = timeSheetRepository.findById(id).orElseThrow();

        timeSheetMapper.updateTimeSheet(timeSheet, request);

        TimeSheet updatedTimeSheet = timeSheetRepository.save(timeSheet);

        return timeSheetMapper.toTimeSheetResponse(updatedTimeSheet);
    }

}
