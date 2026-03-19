package com.xaviervinicius.labschedule.dto;

import java.util.List;

public record SimpleEmail(
        List<String> to,
        String subject,
        String body,
        boolean isHtml
) {
    public SimpleEmail bodiless(){
        return new SimpleEmail(to, subject, "Bodiless", isHtml);
    }
}
