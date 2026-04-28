package com.xaviervinicius.labschedule.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;


public record SimpleEmail(
        List<String> to,
        String subject,
        @JsonIgnore String body,
        boolean isHtml
) {
    public SimpleEmail bodiless(){
        return new SimpleEmail(to, subject, "Bodiless", isHtml);
    }
}
