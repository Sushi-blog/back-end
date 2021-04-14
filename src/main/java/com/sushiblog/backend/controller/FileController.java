package com.sushiblog.backend.controller;

import com.sushiblog.backend.service.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping("/blog/file")
@RequiredArgsConstructor
@RestController
public class FileController {

    private final FileService fileService;

    @GetMapping(value = "/{path}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[] getImages(@PathVariable String path) throws IOException {
        return fileService.getImages(path);
    }

    @DeleteMapping("/{blog-id}")
    public void deleteFile(@PathVariable("blog-id")int blogId) {
        fileService.deleteFile(blogId);
    }

}
