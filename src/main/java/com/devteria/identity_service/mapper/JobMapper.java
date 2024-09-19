package com.devteria.identity_service.mapper;

import com.devteria.identity_service.dto.request.JobRequest;
import com.devteria.identity_service.dto.response.JobResponse;
import com.devteria.identity_service.entity.Job;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface JobMapper {
    Job toJob(JobRequest request);

    JobResponse toJobResponse(Job job);

    void updateJob(@MappingTarget Job job, JobRequest request);
}
