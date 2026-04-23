package com.xaviervinicius.labschedule.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record ScheduleDto(
        UUID id,
        UUID schedulerId,
        Long labId,
        LocalTime start,
        LocalTime end,
        LocalDate date,
        Instant createdAt
) {
}
