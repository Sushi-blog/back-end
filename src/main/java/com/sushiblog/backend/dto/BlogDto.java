package com.sushiblog.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
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

        @NotNull // int형은 NotBlank로 검사하면 안됨
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
        private LocalDate createdAt;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BlogDetailsResponse {

        private Integer categoryId;

        private String writer;

        private String title;

        private String content;

        private LocalDateTime createdAt;

    }

}
