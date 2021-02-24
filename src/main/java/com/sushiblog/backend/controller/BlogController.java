package com.sushiblog.backend.controller;

import com.sushiblog.backend.dto.BlogDto.*;
import com.sushiblog.backend.service.post.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/blog")
@RequiredArgsConstructor
@RestController
public class BlogController {

    private final BlogService blogService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void writePost(@RequestBody BlogRequest request) {
        blogService.writePost(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePost(@PathVariable int id,
                           @RequestBody BlogRequest request) {
        blogService.updatePost(id, request);
    }

    @DeleteMapping
    public void deletePost(@RequestParam int id) {
        blogService.deletePost(id);
    }

    @GetMapping("/{email}")
    public BlogsResponse getBlogs(@PathVariable String email,
                                     Pageable page,
                                     @RequestParam int categoryId) {
        return blogService.getPosts(email, page, categoryId);
    }

    @GetMapping("/details/{email}")
    public BlogDetailsResponse getBlog(@PathVariable String email, @RequestParam int id) {
        return blogService.getPost(email, id);
    }

}
