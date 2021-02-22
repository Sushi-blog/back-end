package com.sushiblog.backend.controller;

import com.sushiblog.backend.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
