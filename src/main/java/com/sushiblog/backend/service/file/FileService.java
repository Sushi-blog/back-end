package com.sushiblog.backend.service.file;

import java.io.IOException;

public interface FileService {
    byte[] getImages(String path) throws IOException;
    void deleteFile(int blogId);
}
