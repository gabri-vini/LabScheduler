package com.xaviervinicius.labschedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EnableUserDto(@NotBlank Long code,@NotNull UUID userId) {
}
