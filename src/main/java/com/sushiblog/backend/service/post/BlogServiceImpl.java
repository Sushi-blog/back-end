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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogServiceImpl implements BlogService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final BlogRepository blogRepository;

    private final AuthenticationFacade authenticationFacade;

    @Value("${file.path}")
    private String PATH;

    @Override
    public void writePost(BlogRequest request, MultipartFile file) throws IOException {
        User user = userRepository.findById(authenticationFacade.getUserEmail())
                .orElseThrow(NotAccessibleException::new);

        Category category = categoryRepository.findById(request.getCategoryId());
        if(category == null) {
            throw new CategoryNotFoundException();
        }

        if(category.getUser().getEmail().equals(user.getEmail())) {
            blogRepository.save(
                    Blog.builder()
                            .title(request.getTitle())
                            .content(request.getContent())
                            .category(category)
                            .createdAt(LocalDateTime.now())
                            .filePath(filePath(file))
                            .build()
            );
            file.transferTo(new File(PATH+file.getOriginalFilename()));
        }
        else {
            throw new NotAccessibleException();
        }
    }

    private String filePath(MultipartFile file) {
        if(file.isEmpty()) {
            return null;
        }
        else {
            return PATH + file.getOriginalFilename();
        }
    }

    @Override
    public void updatePost(int id, BlogRequest request) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(BlogNotFoundException::new);

        User user = userRepository.findById(authenticationFacade.getUserEmail())
                .orElseThrow(NotAccessibleException::new);

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
                .orElseThrow(NotAccessibleException::new);

        if(blog.getCategory().getUser() == user) {
            blogRepository.delete(blog);
            File file = new File(blog.getFilePath());
            file.delete();
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
                      .category(blog.getCategory().getName())
                      .title(blog.getTitle())
                      .createdAt(LocalDate.from(blog.getCreatedAt()))
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
                .categoryId(blog.getCategory().getId())
                .writer(user.getNickname())
                .title(blog.getTitle())
                .content(blog.getContent())
                .createdAt(LocalDateTime.parse(blog.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm"))))
                //날짜와 시간 사이의 공백에 'T'를 붙여준다. 공백이 오류의 원인이 될수 있기 때문에 그렇게 하며, T만 쓰면 오류나서 ''로 묶어주는 것이다.
                .filePath(blog.getFilePath())
                .build();
    }

}
