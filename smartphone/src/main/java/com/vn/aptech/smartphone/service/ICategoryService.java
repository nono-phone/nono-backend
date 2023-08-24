package com.vn.aptech.smartphone.service;

import com.vn.aptech.smartphone.entity.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    List<Category> get();
    Category add(Category categories) throws Exception;
//    Optional<Category> add1(Category categories);
    Category update();
    void delete(Long id);
    Category getById(Long id);

    List<Category> getByParentId(Long id);
}
