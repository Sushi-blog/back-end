package com.sushiblog.backend.service.user;

import com.sushiblog.backend.dto.UserDto.*;
import com.sushiblog.backend.entity.category.Category;
import com.sushiblog.backend.entity.category.CategoryRepository;
import com.sushiblog.backend.entity.user.User;
import com.sushiblog.backend.entity.user.UserRepository;
import com.sushiblog.backend.error.EmailAlreadyExistsException;
import com.sushiblog.backend.error.NicknameAlreadyExistsException;
import com.sushiblog.backend.error.UserNotFoundException;
import com.sushiblog.backend.security.jwt.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;

    private final AuthenticationFacade authenticationFacade;


    @Override
    public void signUp(SignUpRequest signInRequest) {
        userRepository.findByNickname(signInRequest.getNickname())
                .ifPresent(e -> {
                    throw new NicknameAlreadyExistsException();
                });
        userRepository.findById(signInRequest.getEmail())
                .ifPresent(e -> {
                    throw new EmailAlreadyExistsException();
                });

        User user = userRepository.save(
                User.builder()
                        .email(signInRequest.getEmail())
                        .password(passwordEncoder.encode(signInRequest.getPassword()))
                        .nickname(signInRequest.getNickname())
                        .build()
        );
        for(int i = 0; i < 4; i++) {
            categoryRepository.save(
                    Category.builder()
                            .user(user)
                            .name("연어초밥")
                            .build()
            );
        }
    }

    @Override
    public void updateName(String nickname) {
        User user = userRepository.findById(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        user.updateNickname(nickname);
    }

    @Override
    public ProfileResponse profileInfo(String email) {
        User user = userRepository.findById(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        //프로필의 주인
        User owner = userRepository.findById(email)
                .orElseThrow(UserNotFoundException::new);

        return ProfileResponse.builder()
                .email(owner.getEmail())
                .isMine(user.getEmail().equals(email))
                .nickname(owner.getNickname())
                .build();
    }

    @Override
    public void deleteUser() {
        User user = userRepository.findById(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        userRepository.delete(user);
    }
}
