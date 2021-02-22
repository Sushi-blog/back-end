package com.sushiblog.backend.service.category;

import com.sushiblog.backend.dto.CategoryDto;

public interface CategoryService {
    void updateName(int id, String name);
    CategoryDto.Get getList(String email);
}
