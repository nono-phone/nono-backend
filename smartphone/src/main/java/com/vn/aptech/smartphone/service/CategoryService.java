package com.vn.aptech.smartphone.service;

import com.vn.aptech.smartphone.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> get();
    Category add(Category categories) throws Exception;
//    Optional<Category> add1(Category categories);
    Category update(Category category, Long id);
    void delete(Long id);
    Category getById(Long id);

    List<Category> getByParentId(Long id);
}
