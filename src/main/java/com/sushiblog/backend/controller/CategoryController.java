package com.sushiblog.backend.controller;

import com.sushiblog.backend.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.sushiblog.backend.dto.CategoryDto.*;

@RequestMapping("/category")
@RequiredArgsConstructor
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @PutMapping
    public void updateName(@RequestBody Update request) {
        categoryService.updateName(request.getId(), request.getName());
    }

    @GetMapping
    public CategoriesResponse getCategories(@PathVariable String email) {
        return categoryService.getList(email);
    }

}
