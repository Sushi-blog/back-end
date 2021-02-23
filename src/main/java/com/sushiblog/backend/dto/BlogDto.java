package com.sushiblog.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

public class BlogDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class BlogRequest {

        @NotBlank
        private String title;

        @NotBlank
        private String content;

        private String fileName;

        @NotBlank
        private int categoryId;

    }

}
