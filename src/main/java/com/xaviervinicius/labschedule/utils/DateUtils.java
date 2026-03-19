package com.xaviervinicius.labschedule.utils;

import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@UtilityClass
public class DateUtils {
    public Instant now(){
        return LocalDateTime.now().toInstant(ZoneOffset.ofHours(-3));
    }
}
