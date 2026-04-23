package com.xaviervinicius.labschedule.configs;

import com.xaviervinicius.labschedule.models.scheduleModel.Period;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Map;

@Component
@ConfigurationProperties("application.classes")
@Data
public class ClassesConfigs {
    private Map<Period, ClassValues> periods;
    private int classesBeforeBreaktime;
    private int BreaktimeDurationMinutes;
    private int classDurationMinutes;

    @Data
    public static final class ClassValues{
        @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
        private LocalTime startAt;
        @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
        private LocalTime endAt;
    }
}
