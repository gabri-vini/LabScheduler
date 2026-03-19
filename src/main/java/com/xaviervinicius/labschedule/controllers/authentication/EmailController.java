package com.xaviervinicius.labschedule.controllers.authentication;

import com.xaviervinicius.labschedule.dto.responses.SendEmailResponse;
import com.xaviervinicius.labschedule.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/v1/lab-scheduler/emails")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/account-verification")
    public CompletableFuture<ResponseEntity<SendEmailResponse>> sendRegisterVerificationCode(@RequestParam UUID id){
        return emailService.sendRegisterVerificationCodeEmail(id)
                .thenApply(ResponseEntity::ok);
    }
}
