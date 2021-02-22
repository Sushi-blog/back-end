package com.sushiblog.backend.service.category;

import com.sushiblog.backend.dto.CategoryDto.CategoriesResponse;

public interface CategoryService {
    void updateName(int id, String name);
    CategoriesResponse getList(String email);
}
