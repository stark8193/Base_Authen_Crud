package com.devteria.identity_service.mapper;

import com.devteria.identity_service.dto.request.TimeSheetRequest;
import com.devteria.identity_service.dto.response.TimeSheetResponse;
import com.devteria.identity_service.entity.TimeSheet;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TimeSheetMapper {
    TimeSheet toTimeSheet(TimeSheetRequest request);

    TimeSheetResponse toTimeSheetResponse(TimeSheet timeSheet);

    void updateTimeSheet(@MappingTarget TimeSheet timeSheet, TimeSheetRequest request);
}
