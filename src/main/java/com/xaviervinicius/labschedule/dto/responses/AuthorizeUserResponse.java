package com.xaviervinicius.labschedule.dto.responses;

import com.xaviervinicius.labschedule.models.userModel.Role;

import java.util.UUID;

public record AuthorizeUserResponse(UUID userId, Role role) {
}
