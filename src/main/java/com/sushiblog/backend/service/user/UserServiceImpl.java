package com.sushiblog.backend.service.user;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import com.sushiblog.backend.dto.UserDto.*;
import com.sushiblog.backend.entity.category.Category;
import com.sushiblog.backend.entity.category.CategoryRepository;
import com.sushiblog.backend.entity.user.User;
import com.sushiblog.backend.entity.user.UserRepository;
import com.sushiblog.backend.error.*;
import com.sushiblog.backend.security.jwt.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        final String PW_PATTERN = "(?=.*[a-zA-Z])(?=.*[0-9])";
        String password = signInRequest.getPassword();
        Matcher m = Pattern.compile(PW_PATTERN).matcher(password);
        if(m.find()&& password.length() <= 16 && password.length() >= 8) {
            User user = userRepository.save(
                    User.builder()
                            .email(signInRequest.getEmail())
                            .password(passwordEncoder.encode(signInRequest.getPassword()))
                            .nickname(signInRequest.getNickname())
                            .build()
            );
            for(int i = 1; i <= 4; i++) {
                categoryRepository.save(
                        Category.builder()
                                .user(user)
                                .name("카테고리"+i)
                                .build()
                );
            }
        }
        else {
            throw new PasswordFormatInCorrectException();
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
        //프로필의 주인
        User owner = userRepository.findById(email)
                .orElseThrow(UserNotFoundException::new);

        return ProfileResponse.builder()
                .email(owner.getEmail())
                .nickname(owner.getNickname())
                .build();
    }

    @Override
    public void deleteUser() {
        User user = userRepository.findById(authenticationFacade.getUserEmail())
                .orElseThrow(NotAccessibleException::new);

        userRepository.delete(user);
    }
}
