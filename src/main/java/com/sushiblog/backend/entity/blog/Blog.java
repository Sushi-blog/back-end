package com.sushiblog.backend.entity.blog;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sushiblog.backend.dto.BlogDto;
import com.sushiblog.backend.entity.category.Category;
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

    private String title;

    private String content;

    private String fileName;

    @DateTimeFormat(pattern = "yyyy-MM-dd`T`hh:mm:ss") // 띄어쓰기로 인해서 값이 잘못 넘어올까봐 T를 쓰는데, T를 포맷에서 그냥 쓸수가 없어서 ``로 감싸준다.
    private LocalDateTime createdAt;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "category_id")
    private Category category;

    public void update(BlogDto.BlogRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
        this.fileName = request.getFileName();
    }

}
