package com.sushiblog.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sushiblog.backend.dto.BlogDto.*;
import com.sushiblog.backend.entity.blog.Blog;
import com.sushiblog.backend.entity.blog.BlogRepository;
import com.sushiblog.backend.entity.category.Category;
import com.sushiblog.backend.entity.category.CategoryRepository;
import com.sushiblog.backend.entity.user.User;
import com.sushiblog.backend.entity.user.UserRepository;
import com.sushiblog.backend.error.UserNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BackEndApplication.class)
@ActiveProfiles("test")
class BlogControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        User user = userRepository.save(
                User.builder()
                        .nickname("대마고이승윤")
                        .email("201413lsy@dsm.hs.kr")
                        .password(passwordEncoder.encode("password1234"))
                        .build()
        );

        userRepository.save(
                User.builder()
                        .nickname("취업할 수 있을까")
                        .email("yyuunn17@naver.com")
                        .password(passwordEncoder.encode("password1235"))
                        .build()
        );
    }

    @AfterEach
    public void deleteAll() {
        userRepository.deleteAll();
        blogRepository.deleteAll();
        categoryRepository.deleteAll(); //매핑 관계에서는 관련 있는거 다 지우기
    }

    @WithMockUser(value = "201413lsy@dsm.hs.kr", password = "password1234")
    @Test
    public void 게시글작성_성공() throws Exception {
        BlogRequest request = BlogRequest.builder()
                .title("title")
                .content("content")
                .fileName("공부하기 싫다.png")
                .categoryId(createCategory(userRepository.findById("201413lsy@dsm.hs.kr").orElseThrow(UserNotFoundException::new)))
                .build();

        mvc.perform(post("/blog")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @WithMockUser(value = "yyuunn17@naver.com", password = "password1235")
    @Test
    public void 게시글작성_실패() throws Exception {

        BlogRequest request = BlogRequest.builder()
                .title("title")
                .content("content")
                .fileName("공부하기 싫다.png")
                .categoryId(createCategory(userRepository.findById("201413lsy@dsm.hs.kr").orElseThrow(UserNotFoundException::new)))
                .build();

        mvc.perform(post("/blog")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(value = "yyuunn17@naver.com", password = "password1235")
    @Test
    public void 게시글작성_실패_없는카테고리() throws Exception {

        BlogRequest request = BlogRequest.builder()
                .title("title")
                .content("content")
                .fileName("공부하기 싫다.png")
                .categoryId(115)
                .build();

        mvc.perform(post("/blog")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(value = "yyuunn17@naver.com", password = "password1235")
    @Test
    public void 게시글수정_성공() throws Exception {
        User user = userRepository.findById("yyuunn17@naver.com").orElseThrow(UserNotFoundException::new);
        Integer categoryId = createCategory(user);
        Integer blogId = createPost(user, categoryRepository.findById(categoryId).orElseThrow());

        BlogRequest request = BlogRequest.builder()
                .title("title")
                .content("content")
                .fileName("공부하기 싫다.png")
                .categoryId(115)
                .build();

        mvc.perform(put("/blog/"+blogId)
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(value = "yyuunn17@naver.com", password = "password1235")
    @Test
    public void 게시글삭제_성공() throws Exception {
        User user = userRepository.findById("yyuunn17@naver.com").orElseThrow(UserNotFoundException::new);
        Integer categoryId = createCategory(user);
        Integer blogId = createPost(user, categoryRepository.findById(categoryId).orElseThrow());

        mvc.perform(delete("/blog")
                .param("id",blogId.toString()))
                .andExpect(status().isOk());
    }

    private Integer createCategory(User user) {
        return categoryRepository.save(
                Category.builder()
                        .user(user)
                        .id(1)
                        .name("category1")
                        .build()
        ).getId();
    }

    private Integer createPost(User user, Category category) {
        return blogRepository.save(
                Blog.builder()
                        .category(category)
                        .fileName("file")
                        .createdAt(LocalDateTime.now())
                        .title("title")
                        .content("content")
                        .user(user)
                        .build()
        ).getId();
    }

}
