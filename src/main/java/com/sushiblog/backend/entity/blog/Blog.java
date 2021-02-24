package com.sushiblog.backend.entity.blog;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sushiblog.backend.dto.BlogDto.BlogRequest;
import com.sushiblog.backend.entity.category.Category;
import com.sushiblog.backend.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tbl_blog")
public class Blog {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 15)
    private String title;

    @Column(length = 1000)
    private String content;

    private String fileName;

    @DateTimeFormat(pattern = "yyyy-MM-dd`T`hh:mm:ss") // 띄어쓰기로 인해서 값이 잘못 넘어올까봐 T를 쓰는데, T를 포맷에서 그냥 쓸수가 없어서 ``로 감싸준다.
    private LocalDateTime createdAt;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "user_email")
    private User user;

    public void update(BlogRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
        this.fileName = request.getFileName();
    }

}
