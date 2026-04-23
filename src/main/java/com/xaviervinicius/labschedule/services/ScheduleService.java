package com.xaviervinicius.labschedule.services;

import com.xaviervinicius.labschedule.models.labModel.LabState;
import com.xaviervinicius.labschedule.models.scheduleModel.ScheduleModel;
import com.xaviervinicius.labschedule.repository.SchedulerRepositoy.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public Map<Long, ScheduleModel> getAllAvailable(){
        return scheduleRepository.findAllAvailableWithLabLoaded(LabState.AVAILABLE).stream()
                .collect(Collectors.toMap(
                        s -> s.getLab().getId(),
                        Function.identity()
                ));
    }
}
