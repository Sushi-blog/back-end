package com.sushiblog.backend.service.post;

import com.sushiblog.backend.dto.BlogDto.*;
import com.sushiblog.backend.entity.blog.Blog;
import com.sushiblog.backend.entity.blog.BlogRepository;
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

@RequiredArgsConstructor
@Service
public class BlogServiceImpl implements BlogService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final BlogRepository blogRepository;

    private final AuthenticationFacade authenticationFacade;

    @Override
    public void writePost(BlogRequest request) {
        User user = userRepository.findById(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(CategoryNotFoundException::new);

        if(category.getUser().getEmail().equals(user.getEmail())) {
            blogRepository.save(
                    Blog.builder()
                            .title(request.getTitle())
                            .content(request.getContent())
                            .fileName(request.getFileName())
                            .category(category)
                            .build()
            );
        }
        else {
            throw new NotAccessibleException();
        }
    }

}
