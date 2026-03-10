package com.xaviervinicius.labschedule.repository.SchedulerRepositoy;

import com.xaviervinicius.labschedule.models.scheduleModel.ScheduleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<ScheduleModel, UUID> {
}
