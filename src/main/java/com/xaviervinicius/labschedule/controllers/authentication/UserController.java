package com.xaviervinicius.labschedule.controllers.authentication;

import com.xaviervinicius.labschedule.dto.EnableUserDto;
import com.xaviervinicius.labschedule.dto.UserDto;
import com.xaviervinicius.labschedule.dto.UserLowInfoDto;
import com.xaviervinicius.labschedule.dto.mappers.UserMapper;
import com.xaviervinicius.labschedule.dto.responses.AuthorizeUserResponse;
import com.xaviervinicius.labschedule.dto.responses.DenyUserResponse;
import com.xaviervinicius.labschedule.models.userModel.UserModel;
import com.xaviervinicius.labschedule.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @PostMapping("/authorize-user")
    public ResponseEntity<AuthorizeUserResponse> authorizeUser(@AuthenticationPrincipal UserModel admin,
                                                               @RequestParam UUID userId){
        UserModel user = userService.authorizeRegistering(userId, admin.getId());

        return ResponseEntity.ok(new AuthorizeUserResponse(user.getId(), user.getRole()));
    }

    @PostMapping("/deny-user")
    public ResponseEntity<DenyUserResponse> denyUser(@AuthenticationPrincipal UserModel admin,
                                                     @RequestParam UUID userId){
        UserModel user = userService.denyRegistering(userId, admin.getId());

        return ResponseEntity.ok(new DenyUserResponse(user.getId(), "Denied"));
    }

    @GetMapping("/unauthorized-users")
    public ResponseEntity<List<UserLowInfoDto>> getUnauthorizedUsers(){
        return ResponseEntity.ok(userService.getUnauthorizedUsers().stream().map(mapper::mapLowInfo).toList());
    }
}
