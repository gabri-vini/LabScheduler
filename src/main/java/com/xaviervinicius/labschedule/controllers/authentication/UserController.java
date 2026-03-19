package com.xaviervinicius.labschedule.controllers.authentication;

import com.xaviervinicius.labschedule.dto.EnableUserDto;
import com.xaviervinicius.labschedule.dto.UserDto;
import com.xaviervinicius.labschedule.dto.mappers.UserMapper;
import com.xaviervinicius.labschedule.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/lab-scheduler/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper mapper;

    @PostMapping("/verify-account")
    public ResponseEntity<UserDto> verifyAccount(@RequestBody @Valid EnableUserDto data){
        return ResponseEntity.ok(mapper.map(userService.verifyAccount(data)));
    }
}
