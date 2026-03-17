package com.xaviervinicius.labschedule.dto;

import com.xaviervinicius.labschedule.models.UserModel.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.Objects;

public record CreateUserDto(
        @NotBlank(message = "Name can't be missing") String name,
        @Email(message = "Given email isn't a valid email") @NotBlank(message = "Email can't be missing") String email,
        @NotBlank(message = "Password can't be missing")
        @Pattern.List({
                @Pattern(regexp = "^.{8,}$", message = "Password must have at least 8 characters"),
                @Pattern(regexp = ".*[!@#$%^&*(),.?\":{}|<>].*", message = "Password must have a special character"),
                @Pattern(regexp = ".*[A-Z].*", message = "Password must have an uppercase letter"),
                @Pattern(regexp = ".*[a-z].*", message = "Password must have a lowercase letter"),
                @Pattern(regexp = ".*[0-9].*", message = "Password must have a number")}
        )
        String password,
        @NotBlank(message = "confirm password can't be missing") String confirmPassword,
        @NotNull(message = "role can't be missing") Role role
) {
    public boolean samePasswords(){
        return Objects.equals(password, confirmPassword);
    }
    public boolean isAdminCreation(){return role == Role.ADMIN;}
}
