package com.sushiblog.backend.entity.category;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sushiblog.backend.entity.blog.Blog;
import com.sushiblog.backend.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tbl_category")
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 20)
    private String name;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade=CascadeType.ALL, mappedBy = "category")
    @JsonBackReference
    private List<Blog> blogs;

    public void updateName(String name) {
        this.name = name;
    }

}
