package com.sushiblog.backend.controller;

import com.sushiblog.backend.service.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/blog/file")
@RequiredArgsConstructor
@RestController
public class FileController {

    private final FileService fileService;

    @GetMapping
    public void downloadFile() {

    }

    @PostMapping
    public void uploadFile() {

    }

    @DeleteMapping("/{file-id}")
    public void deleteFile(@PathVariable("blog-id")int blogId) {
        fileService.deleteFile(blogId);
    }

}
