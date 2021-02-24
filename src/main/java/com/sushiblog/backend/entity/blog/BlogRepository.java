package com.sushiblog.backend.entity.blog;

import com.sushiblog.backend.entity.category.Category;
import com.sushiblog.backend.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends CrudRepository<Blog, Integer> {
    Page<Blog> findAllByUserAndCategoryOOrderByIdDesc(User user, Category category, Pageable page);
    Page<Blog> findAllByUserOrderByIdDesc(User user, Pageable page);
}
