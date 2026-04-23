package com.xaviervinicius.labschedule.services;

import com.mailgun.api.v3.MailgunMessagesApi;
import com.mailgun.model.message.Message;
import com.xaviervinicius.labschedule.dto.SimpleEmail;
import com.xaviervinicius.labschedule.dto.responses.SendEmailResponse;
import com.xaviervinicius.labschedule.exceptions.UnauthorizedException;
import com.xaviervinicius.labschedule.models.userModel.AccountState;
import com.xaviervinicius.labschedule.models.userModel.UserModel;
import com.xaviervinicius.labschedule.models.verificationCodeModel.VerificationCodeModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class EmailService {
    private final String domain;
    private final String from;

    private final MailgunMessagesApi api;
    private final UserService userService;
    private final TemplateEngine templateEngine;
    private final VerificationCodeService verificationCodeService;

    public EmailService(MailgunMessagesApi api,
                        @Value("${application.emails.domain}") String domain,
                        @Value("${application.emails.from}") String from,
                        UserService userService,
                        TemplateEngine templateEngine,
                        VerificationCodeService verificationCodeService){
        this.api = api;
        this.domain = domain;
        this.from = from;
        this.userService = userService;
        this.templateEngine = templateEngine;
        this.verificationCodeService = verificationCodeService;
    }
    public CompletableFuture<SendEmailResponse> sendAsync(SimpleEmail email){
        return api.sendMessageFeignResponseAsync(this.domain, this.buildMessage(email))
                .handle((result, err) -> {
                    if(err != null){
                        log.error("Error while trying to send email", err);
                        return new SendEmailResponse(false, email, err.getMessage());
                    }
                    if(HttpStatusCode.valueOf(result.status()).is2xxSuccessful()){
                        log.info("Email sent successfully to {}", email.to());
                        return new SendEmailResponse(true, email, null);
                    }
                    log.error("Could not send email. Api return code {}", result.status());
                    return new SendEmailResponse(false, email, "Email wasn't sent, api returned status: " + result.status());
                });
    }

    public CompletableFuture<SendEmailResponse> sendRegisterVerificationCodeEmail(UUID userId){
        UserModel user = userService.getUser(userId);
        if(user.getState() != AccountState.REGISTERING)
            throw new UnauthorizedException("User already finished registering");

        VerificationCodeModel verificationCode = verificationCodeService.requireCode(userId);

        Context thContext = new Context();
        thContext.setVariable("code", verificationCode.getCode());

        String processedHtml = templateEngine.process("verificationCode", thContext);

        SimpleEmail email = new SimpleEmail(
                List.of(user.getEmail()),
                "Verify your account",
                processedHtml,
                true
        );

        return this.sendAsync(email)
            .thenApply(resp -> new SendEmailResponse(
                resp.sent(),
                resp.email().bodiless(),
                resp.message()
            ));
    }

    public Message buildMessage(SimpleEmail email){
        Message.MessageBuilder builder = Message.builder()
                .to(email.to())
                .from(from)
                .subject(email.subject());
        if(email.isHtml()){
            builder.html(email.body());
        }else{
            builder.text(email.body());
        }

        return builder.build();
    }
}
