package com.sushiblog.backend.controller;

import com.sushiblog.backend.dto.UserDto;
import com.sushiblog.backend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/account")
@RestController
public class UserController {
    
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody @Validated UserDto.signUp request) {
        userService.signUp(request);
    }

}