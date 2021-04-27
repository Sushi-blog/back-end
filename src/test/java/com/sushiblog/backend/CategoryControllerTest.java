package com.sushiblog.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sushiblog.backend.dto.CategoryDto.*;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BackEndApplication.class)
@ActiveProfiles("test")
public class CategoryControllerTest {

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

        categoryRepository.save(
                Category.builder()
                        .user(user)
                        .id(1)
                        .name("category1")
                        .build()
        );

        categoryRepository.save(
                Category.builder()
                        .user(user)
                        .id(2)
                        .name("category2")
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
    public void 카테고리명수정() throws Exception {
        Integer id = createCategory(userRepository.findById("201413lsy@dsm.hs.kr").orElseThrow(UserNotFoundException::new));

        UpdateRequest request = new UpdateRequest(id,"sushi카테고리");

        mvc.perform(put("/sushi/category")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(value = "201413lsy@dsm.hs.kr", password = "password1234")
    @Test
    public void 카테고리명수정_실패() throws Exception {
        Integer id = createCategory(userRepository.findById("201413lsy@dsm.hs.kr").orElseThrow(UserNotFoundException::new));
        UpdateRequest request = new UpdateRequest(id,"sushi카테고리20자 넘지 않았냐 왜 오류 안뜨냐");

        mvc.perform(put("/sushi/category")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(value = "yyuunn17@naver.com", password = "password1235")
    @Test
    public void 카테고리명수정_주인X() throws Exception {
        Integer id = createCategory(userRepository.findById("201413lsy@dsm.hs.kr").orElseThrow(UserNotFoundException::new));
        UpdateRequest request = new UpdateRequest(id,"sushi");

        mvc.perform(put("/sushi/category")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isUnauthorized());
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

    @Test
    public void 카테고리리스트() throws Exception {
        mvc.perform(get("/sushi/category/201413lsy@dsm.hs.kr"))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void 카테고리리스트_가져오기실패() throws Exception {
        mvc.perform(get("/sushi/category/201413lsy0@dsm.hs.kr"))
                .andExpect(status().isNotFound()).andDo(print());
    }

}
