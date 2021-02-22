package com.sushiblog.backend.service.user;

import com.sushiblog.backend.dto.UserDto;
import com.sushiblog.backend.entity.user.User;
import com.sushiblog.backend.entity.user.UserRepository;
import com.sushiblog.backend.error.EmailAlreadyExistsException;
import com.sushiblog.backend.error.NicknameAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void signUp(UserDto.signUp signInRequest) {
        userRepository.findByNickname(signInRequest.getNickname())
                .ifPresent(e -> {
                    throw new NicknameAlreadyExistsException();
                });
        userRepository.findById(signInRequest.getEmail())
                .ifPresent(e -> {
                    throw new EmailAlreadyExistsException();
                });

        userRepository.save(
                User.builder()
                        .email(signInRequest.getEmail())
                        .password(passwordEncoder.encode(signInRequest.getPassword()))
                        .nickname(signInRequest.getNickname())
                        .build()
        );
    }

    @Override
    public void updateName(String nickname) {

    }

    @Override
    public UserDto.profile profile(String email) {
        return null;
    }
}
