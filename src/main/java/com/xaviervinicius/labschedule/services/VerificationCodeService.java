package com.xaviervinicius.labschedule.services;

import com.xaviervinicius.labschedule.exceptions.NoSuchVerificationCodeException;
import com.xaviervinicius.labschedule.exceptions.TooManyVerificationCodeRequestsException;
import com.xaviervinicius.labschedule.exceptions.UserNotFoundException;
import com.xaviervinicius.labschedule.models.userModel.UserModel;
import com.xaviervinicius.labschedule.models.verificationCodeModel.VerificationCodeModel;
import com.xaviervinicius.labschedule.repository.UserRepository.UserRepository;
import com.xaviervinicius.labschedule.repository.verificationCode.VerificationCodeRepository;
import com.xaviervinicius.labschedule.utils.DateUtils;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerificationCodeService {
    private static final byte CODE_REQUEST_WAITING_MINUTES = 3;
    private final VerificationCodeRepository verificationCodeRepository;
    private final UserRepository userRepository;

    @Transactional
    public VerificationCodeModel requireCode(@NonNull UUID requesterId){
        UserModel user = userRepository.findById(requesterId).orElseThrow(() -> new UserNotFoundException("User not found"));

        boolean waitingTimeFinished = user.getLastVerificationCodeRequest() == null || DateUtils.now().isAfter(user.getLastVerificationCodeRequest().plus(CODE_REQUEST_WAITING_MINUTES, ChronoUnit.MINUTES));

        if(!waitingTimeFinished)
            throw new TooManyVerificationCodeRequestsException("Too many requests, the waiting time did not finish");

        verificationCodeRepository.deleteByUserId(requesterId);
        VerificationCodeModel verificationCode = verificationCodeRepository.save(new VerificationCodeModel(requesterId));

        user.setLastVerificationCodeRequest(verificationCode.getCreatedAt());
        userRepository.save(user);

        return verificationCode;
    }

    public VerificationCodeModel consume(String code, UUID userId){
        VerificationCodeModel verificationCode = verificationCodeRepository.findByCodeAndUserId(code, userId).orElseThrow(() ->
                new NoSuchVerificationCodeException("No such verification code")
        );

        verificationCodeRepository.delete(verificationCode);
        return verificationCode;
    }
}
