package com.xaviervinicius.labschedule.dto.responses;

import com.xaviervinicius.labschedule.dto.UserDto;
import jakarta.annotation.Nullable;

public record RegisterResponse(
        UserDto user,
        @Nullable String creatorEmail
) { }
