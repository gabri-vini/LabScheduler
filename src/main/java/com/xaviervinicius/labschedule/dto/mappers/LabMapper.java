package com.xaviervinicius.labschedule.dto.mappers;

import com.xaviervinicius.labschedule.dto.LabDto;
import com.xaviervinicius.labschedule.models.labModel.LabModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LabMapper {
    @Mapping(
            target = "schedulesIds",
            expression = "java(model.getSchedules().stream().map(schedule -> schedule.getId()).toList())"
    )
    LabDto map(LabModel model);

    @Mapping(
            target = "schedules",
            expression = "java(dto.schedulesIds().stream().map(LabModel::new).toList())"
    )
    LabModel map(LabDto dto);
}
