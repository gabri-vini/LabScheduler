package com.xaviervinicius.labschedule.dto;

public record SimpleEmail(
        String to,
        String from,
        String subject,
        String body,
        boolean isHtml
) {
}
