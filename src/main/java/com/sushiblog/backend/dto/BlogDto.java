package com.sushiblog.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BlogDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class BlogRequest {

        private String title;

        private String content;

        private String fileName;

        private int categoryId;

    }

}
