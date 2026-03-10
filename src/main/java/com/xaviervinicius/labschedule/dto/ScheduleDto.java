package com.xaviervinicius.labschedule.dto;

import java.time.Instant;
import java.util.UUID;

public record ScheduleDto(
        UUID id,
        UUID schedulerId,
        Long labId,
        Instant start,
        Instant end,
        Instant createdAt
) {
}
