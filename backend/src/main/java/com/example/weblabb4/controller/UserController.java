package com.example.weblabb4.controller;

import com.example.weblabb4.dto.request.AuthRequest;
import com.example.weblabb4.dto.request.RegistrationRequest;
import com.example.weblabb4.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping(path = "/api/v1/users/")
@RestController
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public Long registerUser(@RequestBody RegistrationRequest registrationRequest) {
        return userService.registerUser(registrationRequest);
    }

    @PostMapping("/auth")
    public String auth(@RequestBody AuthRequest authRequest) {

        return userService.authUser(authRequest);

    }
}
