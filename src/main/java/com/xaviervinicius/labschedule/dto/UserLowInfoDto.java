package com.xaviervinicius.labschedule.dto;

import com.xaviervinicius.labschedule.models.UserModel.Role;

import java.util.UUID;

public record UserLowInfoDto(UUID id, String name, String email, Role role) {
}
