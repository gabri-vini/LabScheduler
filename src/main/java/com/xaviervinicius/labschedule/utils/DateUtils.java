package com.xaviervinicius.labschedule.utils;

import lombok.experimental.UtilityClass;

import java.time.*;

@UtilityClass
public class DateUtils {
    public Instant now(){
        return ZonedDateTime.now(ZoneOffset.ofHours(-3)).toInstant();
    }

    public LocalDate today(){
        return ZonedDateTime.now(ZoneOffset.ofHours(-3)).toLocalDate();
    }

    public boolean isWeekend(LocalDate date){
        DayOfWeek day = DayOfWeek.from(date);
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }
}
