package com.xaviervinicius.labschedule.dto.mappers;

import com.xaviervinicius.labschedule.dto.UserDto;
import com.xaviervinicius.labschedule.models.UserModel.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(
            target = "schedulesIds",
            expression = """
                    java(model.getSchedules().stream()
                        .map(schedule -> schedule.getId())
                        .toList()
                    )"""
    )
    UserDto map(UserModel model);

    @Mapping(
            target = "schedules",
            expression = """
                    java(dto.schedulesIds().stream()
                        .map(ScheduleModel::new)
                        .toList()
                    )
                    """
    )
    UserModel map(UserDto dto);
}
