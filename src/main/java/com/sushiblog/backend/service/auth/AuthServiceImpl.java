package com.sushiblog.backend.service.auth;

import com.sushiblog.backend.dto.TokenDto;
import com.sushiblog.backend.dto.UserDto;
import com.sushiblog.backend.entity.refreshToken.RefreshToken;
import com.sushiblog.backend.entity.refreshToken.RefreshTokenRepository;
import com.sushiblog.backend.entity.user.User;
import com.sushiblog.backend.entity.user.UserRepository;
import com.sushiblog.backend.error.InvalidTokenException;
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

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${auth.jwt.exp.refresh}")
    private Long refreshExp;

    @Value("${auth.jwt.exp.access}")
    private Long accessExp;


    @Override
    public TokenDto.TokenResponse signIn(UserDto.signIn signInRequest) {
        return userRepository.findById(signInRequest.getEmail())
                .filter(user -> passwordEncoder.matches(signInRequest.getPassword(), user.getPassword()))
                .map(User::getEmail)
                .map(email -> {
                    String refreshToken = jwtTokenProvider.generateRefreshToken(email);
                    return new RefreshToken(email, refreshToken, refreshExp);
                })
                .map(refreshTokenRepository::save)
                .map(refreshToken -> {
                    String accessToken = jwtTokenProvider.generateAccessToken(refreshToken.getEmail());
                    return new TokenDto.TokenResponse(accessToken, refreshToken.getRefreshToken(), accessExp);
                })
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public TokenDto.AccessTokenResponse tokenRefresh(String receivedToken) {
        if(!jwtTokenProvider.isRefreshToken(receivedToken)) {
            throw new InvalidTokenException();
        }

        return refreshTokenRepository.findByRefreshToken(receivedToken)
                .map(refreshToken ->
                        new TokenDto.AccessTokenResponse(jwtTokenProvider.generateAccessToken(refreshToken.getEmail())))
                .orElseThrow(UserNotFoundException::new);
    }
}
