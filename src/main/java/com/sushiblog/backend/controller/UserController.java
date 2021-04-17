package com.sushiblog.backend.controller;

import com.sushiblog.backend.dto.UserDto.*;
import com.sushiblog.backend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/sushi/account")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody @Validated SignUpRequest request) {
        userService.signUp(request);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateNickname(@RequestBody @Validated UpdateName name) {
        userService.updateName(name.getNickname());
    }

    @DeleteMapping
    public void deleteUser() {
        userService.deleteUser();
    }

    @GetMapping("/{email}")
    ProfileResponse profileInfo(@PathVariable String email) {
        return userService.profileInfo(email);
    }

}
