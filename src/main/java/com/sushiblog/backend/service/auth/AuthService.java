package com.sushiblog.backend.service.auth;


import com.sushiblog.backend.dto.TokenDto;
import com.sushiblog.backend.dto.UserDto.SignInRequest;

public interface AuthService {
    TokenDto.TokenResponse signIn(SignInRequest signInRequest);
    TokenDto.AccessTokenResponse tokenRefresh(String receivedToken);
}
