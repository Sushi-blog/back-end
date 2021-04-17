package com.sushiblog.backend.controller;

import com.sushiblog.backend.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.sushiblog.backend.dto.CategoryDto.*;

@RequestMapping("/sushi/category")
@RequiredArgsConstructor
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateName(@RequestBody @Validated UpdateRequest request) {
        categoryService.updateName(request.getId(), request.getName());
    }

    @GetMapping("/{email}")
    public CategoriesResponse getCategories(@PathVariable String email) {
        return categoryService.getList(email);
    }

}
