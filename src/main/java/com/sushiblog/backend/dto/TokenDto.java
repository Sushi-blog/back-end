package com.sushiblog.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TokenDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TokenResponse {

        @JsonProperty("access-token")
        private String accessToken;

    }

}
