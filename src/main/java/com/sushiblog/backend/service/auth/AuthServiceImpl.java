package com.sushiblog.backend.service.auth;

import com.sushiblog.backend.dto.TokenDto.*;
import com.sushiblog.backend.dto.UserDto.*;
import com.sushiblog.backend.entity.user.User;
import com.sushiblog.backend.entity.user.UserRepository;
import com.sushiblog.backend.error.UserNotFoundException;
import com.sushiblog.backend.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    @Value("${auth.jwt.exp.refresh}")
    private Long refreshExp;

    @Value("${auth.jwt.exp.access}")
    private Long accessExp;


    @Override
    public TokenResponse signIn(SignInRequest request) {
        return userRepository.findById(request.getEmail())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .map(User::getEmail)
                .map(refreshToken -> {
                    String accessToken = jwtTokenProvider.generateAccessToken(request.getEmail());
                    return new TokenResponse(accessToken);
                })
                .orElseThrow(UserNotFoundException::new);
    }
}
