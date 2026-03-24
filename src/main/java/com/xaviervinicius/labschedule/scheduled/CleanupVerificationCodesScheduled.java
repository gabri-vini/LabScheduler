package com.xaviervinicius.labschedule.scheduled;

import com.xaviervinicius.labschedule.models.verificationCodeModel.VerificationCodeModel;
import com.xaviervinicius.labschedule.repository.verificationCode.VerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CleanupVerificationCodesScheduled {
    private final VerificationCodeRepository verificationCodeRepository;

    @Scheduled(cron = "0 0 0 * * *", zone = "America/Sao_Paulo")
    public void cleanupVerificationCodesScheduled(){
        int affectedRows = verificationCodeRepository.deleteAllExpired(VerificationCodeModel.MINUTES_TO_LIVE);

        log.info("Cleaned expired verification codes, affected {} rows", affectedRows);
    }
}
