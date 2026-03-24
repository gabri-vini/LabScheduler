package com.xaviervinicius.labschedule.utils;

import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;

@UtilityClass
public class DateUtils {
    public Instant now(){
        return ZonedDateTime.now(ZoneOffset.ofHours(-3)).toInstant();
    }
}
