package com.sushiblog.backend.controller;

import com.sushiblog.backend.dto.BlogDto.*;
import com.sushiblog.backend.service.post.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/blog")
@RequiredArgsConstructor
@RestController
public class BlogController {

    private final BlogService blogService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void writePost(@RequestBody @Validated BlogRequest request,
                          @RequestParam(required = false) MultipartFile file) throws IOException {
        blogService.writePost(request, file);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePost(@PathVariable int id,
                           @RequestBody @Validated BlogRequest request) {
        blogService.updatePost(id, request);
    }

    @DeleteMapping
    public void deletePost(@RequestParam int id) {
        blogService.deletePost(id);
    }

    @GetMapping("/{email}")
    public BlogsResponse getBlogs(@PathVariable String email,
                                     Pageable page,
                                     @RequestParam(name = "category-id", required = false) int categoryId) {
        return blogService.getPosts(email, page, categoryId);
    }

    @GetMapping("/details/{email}")
    public BlogDetailsResponse getBlog(@PathVariable String email,
                                       @RequestParam int id) {
        return blogService.getPost(email, id);
    }

}
