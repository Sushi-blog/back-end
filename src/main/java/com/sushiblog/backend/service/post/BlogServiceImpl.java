package com.sushiblog.backend.service.post;

import com.sushiblog.backend.dto.BlogDto.*;
import com.sushiblog.backend.entity.blog.Blog;
import com.sushiblog.backend.entity.blog.BlogRepository;
import com.sushiblog.backend.entity.category.Category;
import com.sushiblog.backend.entity.category.CategoryRepository;
import com.sushiblog.backend.entity.user.User;
import com.sushiblog.backend.entity.user.UserRepository;
import com.sushiblog.backend.error.BlogNotFoundException;
import com.sushiblog.backend.error.CategoryNotFoundException;
import com.sushiblog.backend.error.NotAccessibleException;
import com.sushiblog.backend.error.UserNotFoundException;
import com.sushiblog.backend.security.jwt.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

        Category category = categoryRepository.findById(request.getCategoryId());
        if(category == null) {
            throw new CategoryNotFoundException();
        }

        if(category.getUser().getEmail().equals(user.getEmail())) {
            blogRepository.save(
                    Blog.builder()
                            .title(request.getTitle())
                            .content(request.getContent())
                            .fileName(request.getFileName())
                            .category(category)
                            .createdAt(LocalDateTime.now())
                            .build()
            );
        }
        else {
            throw new NotAccessibleException();
        }
    }

    @Override
    public void updatePost(int id, BlogRequest request) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(BlogNotFoundException::new);

        User user = userRepository.findById(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        if(blog.getCategory().getUser() == user) {
            blog.update(request);
        }
        else {
            throw new NotAccessibleException();
        }
    }

    @Override
    public void deletePost(int id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(BlogNotFoundException::new);

        User user = userRepository.findById(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        if(blog.getCategory().getUser() == user) {
            blogRepository.delete(blog);
        }
    }

    @Override
    public BlogsResponse getPosts(String email, Pageable page, int categoryId) {
        User user = userRepository.findById(email)
                .orElseThrow(UserNotFoundException::new);

        Category category = categoryRepository.findById(categoryId);

        Page<Blog> blogPage;
        if(category == null) {
            blogPage = blogRepository.findAllByUserOrderByIdDesc(user, page);
        }
        else {
            blogPage = blogRepository.findAllByUserAndCategoryOrderByIdDesc(user, category, page);
        }
        List<Blogs> blogs = new ArrayList<>();

        for(Blog blog : blogPage) {
            blogs.add(
              Blogs.builder()
                      .id(blog.getId())
                      .category(category)
                      .title(blog.getTitle())
                      .createdAt(blog.getCreatedAt())
                      .build()
            );
        }

        return BlogsResponse.builder()
                .totalElements((int)blogPage.getTotalElements())
                .totalPages(blogPage.getTotalPages())
                .blogs(blogs)
                .build();
    }

    @Override
    public BlogDetailsResponse getPost(String email, int id) {
        User user = userRepository.findById(email)
                .orElseThrow(UserNotFoundException::new);

        Blog blog = blogRepository.findById(id)
                .orElseThrow(BlogNotFoundException::new);

        return BlogDetailsResponse.builder()
                .category(blog.getCategory())
                .writer(user.getNickname())
                .title(blog.getTitle())
                .content(blog.getContent())
                .createdAt(blog.getCreatedAt())
                .build();
    }

}
