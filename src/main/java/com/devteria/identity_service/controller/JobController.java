package com.devteria.identity_service.controller;

import com.devteria.identity_service.dto.request.ApiResponse;
import com.devteria.identity_service.dto.request.JobRequest;
import com.devteria.identity_service.dto.response.JobResponse;
import com.devteria.identity_service.service.JobService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/job")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class JobController {
    JobService jobService;

    @PostMapping
    ApiResponse<JobResponse> createJob(@RequestBody @Valid JobRequest request) {
        return ApiResponse.<JobResponse>builder()
                .data(jobService.createJob(request))
                .build();
    }

    @GetMapping
    ApiResponse<Map<String, Object>> getAllJobWithPage(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort
    ) {
        Map<String, Object> data = jobService.getAllJobWithPage(page, size, sort);

        return ApiResponse.<Map<String, Object>>builder()
                .data(data)
                .build();
    }

    @GetMapping("/{jobId}")
    ApiResponse<JobResponse> getJob(@PathVariable("jobId") String jobId) {
        return ApiResponse.<JobResponse>builder()
                .data(jobService.getJob(jobId))
                .build();
    }

    @PutMapping("/{jobId}")
    ApiResponse<JobResponse> updateJob(@PathVariable("jobId") String jobId, @RequestBody JobRequest request) {
        return ApiResponse. <JobResponse>builder()
                .data(jobService.updateJob(jobId, request))
                .build();
    }

    @DeleteMapping("/{jobId}")
    ApiResponse<String> delete(@PathVariable String jobId) {
        jobService.delete(jobId);
        return ApiResponse.<String>builder().data("Job has been deleted").build();
    }
}
