package com.xaviervinicius.labschedule.controllers.authentication;

import com.xaviervinicius.labschedule.models.scheduleModel.ScheduleModel;
import com.xaviervinicius.labschedule.services.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/lab-scheduler/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<Map<Long, ScheduleModel>> getAllAvailable(){
        return ResponseEntity.ok(scheduleService.getAllAvailable());
    }
}
