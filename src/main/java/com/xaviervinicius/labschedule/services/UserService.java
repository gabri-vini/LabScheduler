package com.xaviervinicius.labschedule.services;

import com.xaviervinicius.labschedule.dto.EnableUserDto;
import com.xaviervinicius.labschedule.exceptions.InvalidCodeException;
import com.xaviervinicius.labschedule.exceptions.UserNotFoundException;
import com.xaviervinicius.labschedule.models.UserModel.AccountState;
import com.xaviervinicius.labschedule.models.UserModel.UserModel;
import com.xaviervinicius.labschedule.repository.UserRepository.UserRepository;
import com.xaviervinicius.labschedule.repository.verificationCode.VerificationCodeRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final VerificationCodeService verificationCodeService;
    private final UserRepository userRepository;

    @Transactional
    public UserModel verifyAccount(@NonNull EnableUserDto data){
        var code = verificationCodeService.consume(data.code(), data.userId());

        if(code.isExpired())
            throw new InvalidCodeException("Code is expired");

        UserModel user = userRepository.findById(data.userId()).orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setState(user.getRole().requiresAdminAuthorization ? AccountState.PENDING_AUTHORIZATION : AccountState.ACTIVE);

        log.info("User of id {} was enabled", user.getId());
        user = userRepository.save(user);

        return user;
    }

    public UserModel getUser(@NonNull UUID id){
        return userRepository.findById(id).orElseThrow();
    }
}
