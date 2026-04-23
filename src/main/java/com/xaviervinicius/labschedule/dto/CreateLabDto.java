package com.xaviervinicius.labschedule.dto;

import com.xaviervinicius.labschedule.models.labModel.LabState;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

public record CreateLabDto(
        @NotBlank String name,
        @Nullable LabState state,
        @Nullable @FutureOrPresent Instant operationReturnTime
) {
}
