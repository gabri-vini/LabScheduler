package com.xaviervinicius.labschedule.dto.responses;

import com.xaviervinicius.labschedule.dto.SimpleEmail;
import jakarta.annotation.Nullable;

public record SendEmailResponse(boolean sent, SimpleEmail email, @Nullable String message) {
}
