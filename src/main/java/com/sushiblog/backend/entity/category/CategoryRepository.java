package com.sushiblog.backend.entity.category;

import com.sushiblog.backend.entity.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {
    List<Category> findAllByUser(User user);
    Optional<Category> findById(int id);
}
