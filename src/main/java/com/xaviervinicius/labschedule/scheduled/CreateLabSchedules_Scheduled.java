package com.xaviervinicius.labschedule.scheduled;

import com.xaviervinicius.labschedule.configs.ClassesConfigs;
import com.xaviervinicius.labschedule.models.labModel.LabModel;
import com.xaviervinicius.labschedule.models.scheduleModel.ScheduleModel;
import com.xaviervinicius.labschedule.repository.SchedulerRepositoy.ScheduleRepository;
import com.xaviervinicius.labschedule.services.LabService;
import com.xaviervinicius.labschedule.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateLabSchedules_Scheduled {
    private final ClassesConfigs classesConfigs;
    private final ScheduleRepository scheduleRepository;
    private final LabService labService;

    @Scheduled(initialDelay = 1, zone = "America/Sao_Paulo")
    public void createLabSchedules_Scheduled(){
        List<ScheduleModel> schedules = new ArrayList<>();
        List<LabModel> labs = labService.getAvailableLabs();

        LocalDate today = DateUtils.today();

        if(DateUtils.isWeekend(today)){
            log.info("Today is weekend, not creating any schedule!");
            return;
        }

        for(LabModel lab : labs){
            classesConfigs.getPeriods().forEach((period, values) -> {
                LocalTime curr = values.getStartAt();
                int classesInPeriod = (int) Duration.between(values.getStartAt(), values.getEndAt()).toMinutes() / classesConfigs.getClassDurationMinutes();

                int classCount = 0;


                while(Duration.between(curr, values.getEndAt()).isPositive()){
                    if((classCount > 0 && classCount < classesInPeriod) && classCount % classesConfigs.getClassesBeforeBreaktime() == 0){
                        curr = curr.plusMinutes(classesConfigs.getBreaktimeDurationMinutes());
                    }

                    ScheduleModel schedule = new ScheduleModel();

                    schedule.setStart(curr);
                    schedule.setEnd(curr.plusMinutes(classesConfigs.getClassDurationMinutes()));
                    schedule.setDate(today);
                    schedule.setLab(lab);

                    curr = curr.plusMinutes(classesConfigs.getClassDurationMinutes());

                    if(!scheduleRepository.thereIsConflict(
                            schedule.getLab().getId(),
                            schedule.getStart(),
                            schedule.getEnd(),
                            schedule.getDate()
                    )) {
                        schedules.add(schedule);
                        classCount++;
                    }
                }
            });
        }
        int total = scheduleRepository.saveAll(schedules).size();
        log.info("Created a total of {} available schedules for {} labs", total, labs.size());
    }
}
