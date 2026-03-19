package com.xaviervinicius.labschedule.services;

import com.mailgun.api.v3.MailgunMessagesApi;
import com.mailgun.model.message.Message;
import com.xaviervinicius.labschedule.dto.SimpleEmail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class EmailService {
    private final MailgunMessagesApi api;
    private final String domain;

    public EmailService(MailgunMessagesApi api, @Value("${application.emails.domain}") String domain){
        this.api = api;
        this.domain = domain;
    }
    public CompletableFuture<Boolean> sendAsync(SimpleEmail email){


        return api.sendMessageFeignResponseAsync(this.domain, this.buildMessage(email))
                .handle((result, err) -> {
                    if(err != null){
                        log.error("Error while trying to send email", err);
                        return false;
                    }
                    if(HttpStatusCode.valueOf(result.status()).is2xxSuccessful()){
                        log.info("Email sent successfully to {}", email.to());
                        return true;
                    }
                    log.error("Could not send email. Api return code {}", result.status());
                    return false;
                });
    }

    public Message buildMessage(SimpleEmail email){
        Message.MessageBuilder builder = Message.builder()
                .to(email.to())
                .from(email.from())
                .subject(email.subject());
        if(email.isHtml()){
            builder.html(email.body());
        }else{
            builder.text(email.body());
        }

        return builder.build();
    }
}
