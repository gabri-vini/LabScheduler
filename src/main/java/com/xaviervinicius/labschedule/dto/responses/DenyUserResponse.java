package com.xaviervinicius.labschedule.dto.responses;

import jakarta.annotation.Nullable;

import java.util.UUID;

public record DenyUserResponse(UUID userId, @Nullable String message) {
}
