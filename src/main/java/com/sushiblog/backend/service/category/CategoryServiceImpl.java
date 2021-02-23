package com.sushiblog.backend.service.category;

import com.sushiblog.backend.dto.CategoryDto.*;
import com.sushiblog.backend.dto.CategoryDto.CategoriesResponse;
import com.sushiblog.backend.entity.category.Category;
import com.sushiblog.backend.entity.category.CategoryRepository;
import com.sushiblog.backend.entity.user.User;
import com.sushiblog.backend.entity.user.UserRepository;
import com.sushiblog.backend.error.CategoryNotFoundException;
import com.sushiblog.backend.error.NotAccessibleException;
import com.sushiblog.backend.error.UserNotFoundException;
import com.sushiblog.backend.security.jwt.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;
    private final CategoryRepository categoryRepository;

    @Override
    public void updateName(int id, String name) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);

        if(category.getUser().getEmail().equals(authenticationFacade.getUserEmail())) {
            category.updateName(name);
        }
        else {
            throw new NotAccessibleException();
        }
    }

    @Override
    public CategoriesResponse getList(String email) {
        User user = userRepository.findById(email)
                .orElseThrow(UserNotFoundException::new);

        List<Category> categories = categoryRepository.findAllByUser(user);
        List<CategoryResponse> categoryList = new ArrayList<>();

        for(Category category : categories) {
            categoryList.add(
                    CategoryResponse.builder()
                            .id(category.getId())
                            .name(category.getName())
                            .build()
            );
        }

        return CategoriesResponse.builder()
                .categories(categoryList)
                .build();
    }


}
