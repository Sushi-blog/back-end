package com.sushiblog.backend.service.post;

import com.sushiblog.backend.dto.BlogDto.*;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface BlogService {
    void writePost(BlogRequest request, MultipartFile file) throws IOException;
    void updatePost(int id, BlogRequest request);
    void deletePost(int id);
    BlogsResponse getPosts(String email, Pageable page, int categoryId);
    BlogDetailsResponse getPost(String email, int id);
}
