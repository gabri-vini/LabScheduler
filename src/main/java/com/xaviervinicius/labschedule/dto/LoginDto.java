package com.xaviervinicius.labschedule.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDto(@NotBlank(message = "Email can't be missing") @Email(message = "Given email isn't a valid email") String email,
                       @NotBlank(message = "Password can't be missing") String password) {
}
