package com.devteria.identity_service.service;

import com.devteria.identity_service.dto.ApiQueryResponse;
import com.devteria.identity_service.dto.request.JobRequest;
import com.devteria.identity_service.dto.response.JobResponse;
import com.devteria.identity_service.entity.Job;
import com.devteria.identity_service.exception.AppException;
import com.devteria.identity_service.exception.ErrorCode;
import com.devteria.identity_service.mapper.JobMapper;
import com.devteria.identity_service.repository.JobRepository;
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
public class JobService {
    JobRepository jobRepository;
    JobMapper jobMapper;

    public JobResponse createJob(JobRequest request){
        Job job = jobMapper.toJob(request);
        job = jobRepository.save(job);
        return jobMapper.toJobResponse(job);
    }

    public Map<String, Object> getAllJobWithPage(Integer page, Integer size, String sort) {
        Sort sortable = null;
        if (sort.equalsIgnoreCase("ASC")) {
            sortable = Sort.by("jobName").ascending();
        } else if (sort.equalsIgnoreCase("DESC")) {
            sortable = Sort.by("jobName").descending();
        }

        Pageable pageable = PageRequest.of(page, size, sortable);

        Page<Job> jobPage = jobRepository.findAll(pageable);

        List<JobResponse> jobs = jobPage.getContent()
                .stream()
                .map(jobMapper::toJobResponse)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("data", jobs);
        response.put("totalItems", jobPage.getTotalElements());
        response.put("totalPages", jobPage.getTotalPages());
        response.put("currentPage", jobPage.getNumber());

        return response;
    }

    public JobResponse getJob(String id){
        return jobMapper.toJobResponse(jobRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND)));
    }

    public JobResponse updateJob(String id, JobRequest request){
        Job department = jobRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.NOT_FOUND));

        jobMapper.updateJob(department, request);

        Job updatedJob = jobRepository.save(department);

        return jobMapper.toJobResponse(updatedJob);
    }

    public void delete(String id) {
        jobRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.NOT_FOUND));
        jobRepository.deleteById(id);
    }

    public List<ApiQueryResponse> query(){
        List<ApiQueryResponse> list = jobRepository.findAll().stream()
                .map(job -> ApiQueryResponse.builder()
                        .id(job.getId())
                        .value(job.getJobName())
                        .build())
                .toList();
        return list;
    }
}
