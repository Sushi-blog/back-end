package com.sushiblog.backend.service.user;

import com.sushiblog.backend.dto.UserDto.*;

public interface UserService {
    void signUp(SignUpRequest signInRequest);
    void updateName(String nickname);
    ProfileResponse profileInfo(String email);
    void deleteUser();
}
