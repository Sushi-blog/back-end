package com.sushiblog.backend.service.user;

import com.sushiblog.backend.dto.UserDto;

public interface UserService {
    void signUp(UserDto.signUp signInRequest);
    void updateName(String nickname);
    UserDto.profile profile(String email);
}
