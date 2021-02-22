package com.sushiblog.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignUpRequest {

        @NotBlank
        @Email
        private String email;

        @NotBlank
        private String password;

        @NotBlank
        private String nickname;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignInRequest {

        @NotBlank
        @Email
        private String email;

        @NotBlank
        private String password;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProfileResponse {

        private String email;

        private boolean isMine;

        private String nickname;

    }

}
