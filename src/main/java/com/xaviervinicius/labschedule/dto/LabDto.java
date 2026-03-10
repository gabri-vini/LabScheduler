package com.xaviervinicius.labschedule.dto;

import com.xaviervinicius.labschedule.models.labModel.LabState;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record LabDto(
        Long id,
        String name,
        LabState state,
        List<UUID> schedulesIds,
        Instant operationReturnTime,
        Instant createdAt
) {}
