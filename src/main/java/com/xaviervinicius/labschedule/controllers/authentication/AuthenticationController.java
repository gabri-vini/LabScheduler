package com.xaviervinicius.labschedule.controllers.authentication;

import com.xaviervinicius.labschedule.controllers.authentication.responses.LoginResponse;
import com.xaviervinicius.labschedule.controllers.authentication.responses.RegisterResponse;
import com.xaviervinicius.labschedule.dto.CreateUserDto;
import com.xaviervinicius.labschedule.dto.LoginDto;
import com.xaviervinicius.labschedule.services.AuthenticationService;
import com.xaviervinicius.labschedule.services.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/lab-scheduler/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid CreateUserDto data,
                                                     @RequestParam(name = "creator_token", required = false) String creatorToken){
        RegisterResponse registerResponse = authenticationService.register(data, creatorToken);

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginDto login){
        return ResponseEntity.ok(new LoginResponse(authenticationService.login(login)));
    }
}