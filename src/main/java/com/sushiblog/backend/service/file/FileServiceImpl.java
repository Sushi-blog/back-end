package com.sushiblog.backend.service.file;

import com.sushiblog.backend.entity.blog.Blog;
import com.sushiblog.backend.entity.blog.BlogRepository;
import com.sushiblog.backend.entity.user.User;
import com.sushiblog.backend.entity.user.UserRepository;
import com.sushiblog.backend.error.BlogNotFoundException;
import com.sushiblog.backend.error.ImageNotFoundException;
import com.sushiblog.backend.error.NotAccessibleException;
import com.sushiblog.backend.error.UserNotFoundException;
import com.sushiblog.backend.security.jwt.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.*;

@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {

    private final AuthenticationFacade authenticationFacade;

    private final UserRepository userRepository;
    private final BlogRepository blogRepository;


    @Override
    public byte[] getImages(String path) throws IOException {
        File file = new File(path);
        if(!file.exists()) throw new ImageNotFoundException();

        InputStream inputStream = new FileInputStream(file);

        return IOUtils.toByteArray(inputStream);
    }

    @Override
    public void deleteFile(int blogId) {
        User user = userRepository.findById(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(BlogNotFoundException::new);

        if(blog.getUser().getEmail().equals(user.getEmail())) {
            File file = new File(blog.getFilePath());
            file.delete();
        }
        else {
            throw new NotAccessibleException();
        }

    }

}
