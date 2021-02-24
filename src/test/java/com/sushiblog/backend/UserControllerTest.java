package com.sushiblog.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sushiblog.backend.dto.UserDto.*;
import com.sushiblog.backend.entity.blog.BlogRepository;
import com.sushiblog.backend.entity.category.CategoryRepository;
import com.sushiblog.backend.entity.user.User;
import com.sushiblog.backend.entity.user.UserRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BackEndApplication.class)
@ActiveProfiles("test")
class UserControllerTest {

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

        userRepository.save(
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

    @Test
    public void 회원가입_중복() throws Exception {
        SignUpRequest request = new SignUpRequest("201413lsy@dsm.hs.kr","password1234","파일은 어떻게하냐");

        mvc.perform(post("/account")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isConflict());
    }

    @Test
    public void 회원가입() throws Exception {
        SignUpRequest request = new SignUpRequest("20214@gmail.com","password09","파일은 어떻게하냐");

        mvc.perform(post("/account")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    public void 회원가입_비밀번호_오류() throws Exception {
        SignUpRequest request = new SignUpRequest("20214@gmail.com","password","파일은 어떻게하냐");

        mvc.perform(post("/account")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void 회원가입_비밀번호_길이오류() throws Exception {
        SignUpRequest request = new SignUpRequest("20214@gmail.com","1234","파일은 어떻게하냐");

        mvc.perform(post("/account")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(value = "201413lsy@dsm.hs.kr", password = "password1234")
    @Test
    public void 회원탈퇴() throws Exception {
        mvc.perform(delete("/account"))
                .andExpect(status().isOk());
    }

    @WithMockUser(value = "a", password = "a")
    @Test
    public void 회원탈퇴_실패() throws Exception {
        mvc.perform(delete("/account"))
                .andExpect(status().isUnauthorized());
    }

}
