package com.sushiblog.backend.service.auth;


import com.sushiblog.backend.dto.TokenDto;
import com.sushiblog.backend.dto.UserDto;

public interface AuthService {
    TokenDto.TokenResponse signIn(UserDto.signIn signInRequest);
    TokenDto.AccessTokenResponse tokenRefresh(String receivedToken);
}
