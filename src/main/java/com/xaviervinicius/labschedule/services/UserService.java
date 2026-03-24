package com.xaviervinicius.labschedule.services;

import com.xaviervinicius.labschedule.dto.EnableUserDto;
import com.xaviervinicius.labschedule.exceptions.InvalidCodeException;
import com.xaviervinicius.labschedule.exceptions.UnauthorizedException;
import com.xaviervinicius.labschedule.exceptions.UserNotFoundException;
import com.xaviervinicius.labschedule.models.UserModel.AccountState;
import com.xaviervinicius.labschedule.models.UserModel.Role;
import com.xaviervinicius.labschedule.models.UserModel.UserModel;
import com.xaviervinicius.labschedule.repository.UserRepository.UserRepository;
import com.xaviervinicius.labschedule.repository.verificationCode.VerificationCodeRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Transactional
    public UserModel  authorizeRegistering(@NonNull UUID toAuthorizeId, @NonNull UUID adminId){
        checkAdmin(adminId);
        UserModel toAuthorize = getUser(toAuthorizeId);

        if(toAuthorize.getState() != AccountState.PENDING_AUTHORIZATION){
            throw new IllegalStateException("User state is not pending authorization");
        }

        toAuthorize.setState(AccountState.ACTIVE);

        userRepository.save(toAuthorize);
        log.info("Admin with id {} authorized registration of user with email: {}", adminId, toAuthorize.getEmail());

        return toAuthorize;
    }

    @Transactional
    public UserModel denyRegistering(@NonNull UUID toAuthorizeId, @NonNull UUID adminId) {
        checkAdmin(adminId);
        UserModel toAuthorize = getUser(toAuthorizeId);

        if(toAuthorize.getState() != AccountState.PENDING_AUTHORIZATION){
            throw new IllegalStateException("User state is not pending authorization");
        }

        toAuthorize.setState(AccountState.BLOCKED);
        userRepository.save(toAuthorize);

        log.info("Admin with id {} denied registration of user with email {}", adminId, toAuthorize.getEmail());

        return toAuthorize;
    }

    public List<UserModel> getUnauthorizedUsers(){
        return userRepository.findAllByState(AccountState.PENDING_AUTHORIZATION);
    }

    public UserModel getUser(@NonNull UUID id){
        return userRepository.findById(id).orElseThrow();
    }

    private void checkAdmin(UUID adminId){
        if(!userRepository.existsByIdAndRoleAndState(adminId, Role.ADMIN, AccountState.ACTIVE))
            throw new UnauthorizedException("Id does not refer to an existent active admin");

    }
}
