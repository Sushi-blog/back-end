package com.sushiblog.backend.service.post;

import com.sushiblog.backend.dto.BlogDto.*;

public interface BlogService {
    void writePost(BlogRequest request);
    void updatePost(int id, BlogRequest request);
}
