package com.sushiblog.backend.service.category;

import com.sushiblog.backend.dto.CategoryDto;
import com.sushiblog.backend.entity.category.Category;
import com.sushiblog.backend.entity.category.CategoryRepository;
import com.sushiblog.backend.error.NotAccessibleException;
import com.sushiblog.backend.security.jwt.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final AuthenticationFacade authenticationFacade;
    private final CategoryRepository categoryRepository;

    @Override
    public void updateName(int id, String name) {
        if(authenticationFacade.isLogin()){
            throw new NotAccessibleException();
        };

        Category category = categoryRepository.findById(id)
                .orElseThrow();
        if(category.getUser().getEmail().equals(authenticationFacade.getUserEmail())) {
            category.updateName(name);
        }
    }

    @Override
    public CategoryDto.Get getList(String email) {
        return null;
    }


}
