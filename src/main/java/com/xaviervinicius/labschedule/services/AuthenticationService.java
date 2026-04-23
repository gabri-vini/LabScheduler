package com.xaviervinicius.labschedule.services;

import com.xaviervinicius.labschedule.dto.responses.RegisterResponse;
import com.xaviervinicius.labschedule.dto.CreateUserDto;
import com.xaviervinicius.labschedule.dto.LoginDto;
import com.xaviervinicius.labschedule.dto.mappers.UserMapper;
import com.xaviervinicius.labschedule.exceptions.EmailAlreadyInUseException;
import com.xaviervinicius.labschedule.exceptions.UnauthorizedException;
import com.xaviervinicius.labschedule.models.userModel.AccountState;
import com.xaviervinicius.labschedule.models.userModel.Role;
import com.xaviervinicius.labschedule.models.userModel.UserModel;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    public static final byte SESSION_DURATION_HOURS = 3;

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
            if(creatorToken == null || creatorToken.isEmpty()){
                throw new UnauthorizedException();
            }
            
            creatorEmail = jwtService.decode(creatorToken);
            //It checks if the user who is trying to create a new admin is an existent admin, and it is active
            if(!userRepository.existsByEmailAndRoleAndState(creatorEmail, Role.ADMIN, AccountState.ACTIVE)){
                throw new UnauthorizedException();
            }
            log.info("Email: {} is trying to create a new admin", creatorEmail);
        }

        if(!data.samePasswords())
            throw new RuntimeException("Passwords doesn't match");

        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(data, userModel, "password");

        String encodedPassword = passwordEncoder.encode(data.password());

        userModel.setPassword(encodedPassword);
        userModel.setState(AccountState.REGISTERING);

        userModel = userRepository.save(userModel);

        return new RegisterResponse(mapper.map(userModel), creatorEmail);
    }

    public String login(@NonNull LoginDto login){
        Authentication auth = new UsernamePasswordAuthenticationToken(login.email(), login.password());
        UserModel user =  (UserModel) authenticationManager.authenticate(auth).getPrincipal();

        if(user == null){
            throw new IllegalStateException("Authentication principal/user is null");
        }

        if(user.getState() != AccountState.ACTIVE){
            throw new RuntimeException("User account is not allowed to make login");
        }
        
        return jwtService.tokenize(user.getEmail(), SESSION_DURATION_HOURS);
    }
}
