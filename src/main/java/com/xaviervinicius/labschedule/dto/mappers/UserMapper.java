package com.xaviervinicius.labschedule.dto.mappers;

import com.xaviervinicius.labschedule.dto.UserDto;
import com.xaviervinicius.labschedule.dto.UserLowInfoDto;
import com.xaviervinicius.labschedule.models.userModel.UserModel;
import com.xaviervinicius.labschedule.models.scheduleModel.ScheduleModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {ScheduleModel.class})
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

    UserLowInfoDto mapLowInfo(UserModel user);

    UserModel map(UserLowInfoDto dto);
}
