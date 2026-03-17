package com.xaviervinicius.labschedule.services;

import com.mailgun.api.v3.MailgunMessagesApi;
import com.mailgun.model.message.Message;
import com.xaviervinicius.labschedule.dto.SimpleEmail;
import com.xaviervinicius.labschedule.dto.mappers.EmailMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class EmailService {
    private final MailgunMessagesApi api;
    private final String domain;

    public EmailService(MailgunMessagesApi api,@Value("${application.email.domain}") String domain){
        this.api = api;
        this.domain = domain;
    }

    public CompletableFuture<Boolean> sendAsync(SimpleEmail email){
        Message msg = EmailMapper.map(email);

        return api.sendMessageFeignResponseAsync(this.domain, msg)
                .handle((resp, err) -> {
                    if(err != null){
                        log.error("Error trying to send email", err);
                        return false;
                    }
                    if(resp.status() == 200){
                        log.info("Email sent successfully to address/adresses: {}", msg.getTo());
                        return true;
                    }
                    return false;
                });
    }
}
