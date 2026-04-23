package com.xaviervinicius.labschedule.dto;

import com.xaviervinicius.labschedule.models.userModel.Role;

import java.util.UUID;

public record UserLowInfoDto(UUID id, String name, String email, Role role) {
}
