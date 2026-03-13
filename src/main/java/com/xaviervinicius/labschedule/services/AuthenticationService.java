package com.xaviervinicius.labschedule.services;

import com.xaviervinicius.labschedule.controllers.authentication.responses.RegisterResponse;
import com.xaviervinicius.labschedule.dto.CreateUserDto;
import com.xaviervinicius.labschedule.dto.LoginDto;
import com.xaviervinicius.labschedule.dto.mappers.UserMapper;
import com.xaviervinicius.labschedule.exceptions.EmailAlreadyInUseException;
import com.xaviervinicius.labschedule.exceptions.UnauthorizedException;
import com.xaviervinicius.labschedule.models.UserModel.AccountState;
import com.xaviervinicius.labschedule.models.UserModel.Role;
import com.xaviervinicius.labschedule.models.UserModel.UserModel;
import com.xaviervinicius.labschedule.repository.UserRepository.UserRepository;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.temporal.Temporal;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper mapper;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public RegisterResponse register(@NonNull CreateUserDto data, @Nullable String creatorToken){
        userRepository.findByEmail(data.email()).ifPresent(u -> {
            throw new EmailAlreadyInUseException("Email " + data.email() + " is already being used");
        });

        /* The algorithm won't proceed if the role is admin and there is not a creator,
         * or if the creator is not an admin
        */
        String creatorEmail = null;

        if(data.isAdminCreation()){
            if(creatorToken != null && !creatorToken.isEmpty()){
                creatorEmail = jwtService.decode(creatorToken);
                if(userRepository.existsByEmailAndRole(creatorEmail, Role.ADMIN)){
                    log.info("Email: {} is trying to create a new admin", creatorEmail);
                }
            }
            throw new UnauthorizedException();
        }

        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(data, userModel, "password");

        String encodedPassword = passwordEncoder.encode(data.password());

        userModel.setPassword(encodedPassword);
        userModel.setState(AccountState.REGISTERING);

        return new RegisterResponse(mapper.map(userModel), creatorEmail);
    }

    public String login(@NonNull LoginDto login){
        Authentication auth = new UsernamePasswordAuthenticationToken(login.email(), login.password());
        UserModel user =  (UserModel) authenticationManager.authenticate(auth).getPrincipal();
        return jwtService.tokenize(user.getEmail(), 3);
    }
}
