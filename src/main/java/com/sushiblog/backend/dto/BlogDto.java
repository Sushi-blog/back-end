package com.sushiblog.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

public class BlogDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BlogRequest { //내부 클래스를 선언할 때는 static 키워드를 붙여준다.

        @NotBlank
        private String title;

        @NotBlank
        private String content;

        private String fileName;

        @NotBlank
        private int categoryId;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BlogsResponse {

        private List<Blogs> blogs;

        private int totalElements;

        private int totalPages;

    }

    @Getter
    @Builder
    public static class Blogs {

        private int id;

        private String category;

        private String title;

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDateTime createdAt;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BlogDetailsResponse {

        private String category;

        private String writer;

        private String content;

        @DateTimeFormat(pattern = "yyyy-MM-dd`T`hh:mm")
        private LocalDateTime createdAt;

    }

}
