package com.sushiblog.backend.entity.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sushiblog.backend.entity.blog.Blog;
import com.sushiblog.backend.entity.category.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tbl_user")
public class User {

    @Id
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 10)
    private String nickname;

    @OneToMany
    @JsonBackReference
    private List<Category> categories;

    @OneToMany
    @JsonBackReference
    private List<Blog> blogs;

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

}
