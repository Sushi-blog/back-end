package com.sushiblog.backend.controller;

import com.sushiblog.backend.dto.TokenDto;
import com.sushiblog.backend.dto.UserDto;
import com.sushiblog.backend.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TokenDto.TokenResponse signIn(@RequestBody UserDto.signIn signInRequest) {
        return authService.signIn(signInRequest);
    }

    @PutMapping
    public TokenDto.AccessTokenResponse tokenRefresh(@RequestHeader("X-Refresh-Token") String refreshToken) {
        return authService.tokenRefresh(refreshToken);
    }

}
