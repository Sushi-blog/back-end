package com.sushiblog.backend.service.auth;


import com.sushiblog.backend.dto.TokenDto.*;
import com.sushiblog.backend.dto.UserDto.SignInRequest;

public interface AuthService {
    TokenResponse signIn(SignInRequest signInRequest);
}
