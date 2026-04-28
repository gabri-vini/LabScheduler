package com.xaviervinicius.labschedule.dto;

import com.xaviervinicius.labschedule.models.userModel.AccountState;
import com.xaviervinicius.labschedule.models.userModel.Role;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record UserDto(
        UUID id,
        String name,
        String email,
        @JsonIgnore String password,
        List<UUID> schedulesIds,
        Role role,
        AccountState state,
        Instant lastVerificationCodeRequest,
        Instant createdAt
) {
}
