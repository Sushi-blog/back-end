package com.sushiblog.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public class CategoryDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoriesResponse{

        List<CategoryResponse> categories;

    }

    @Getter
    @Builder
    public static class CategoryResponse {

        private int id;

        private String name;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateRequest{

        private int id;

        private String name;

    }

}
