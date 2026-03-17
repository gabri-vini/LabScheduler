package com.xaviervinicius.labschedule.dto;

import java.util.List;

public record SimpleEmail(
        List<String> to,
        String from,
        String subject,
        String body,
        boolean isHtml
) {
}
