package com.vn.aptech.smartphone.service.Impl;

import com.vn.aptech.smartphone.entity.Category;
import com.vn.aptech.smartphone.exception.NotFoundException;
import com.vn.aptech.smartphone.exception.ConflictException;
import com.vn.aptech.smartphone.repository.CategoryRepository;
import com.vn.aptech.smartphone.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;

    @Override
    public List<Category> get() {
        return repository.findAll();
    }

    @Override
    public Category add(Category category) throws Exception {
        if (checkDuplicate(category.getName())) {
            return repository.save(category);
        } else {
            throw new ConflictException("Duplicate category name");
        }
    }

//    @Override
//    public Optional<Category> add1(Category categories) {
//        return repository.findByName(categories.getName());
//    }

    @Override
    public Category update() {
        return null;
    }

    @Override
    public void delete(Long id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
        }
    }

    @Override
    public Category getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Not found category with id = %d", id)));
    }

    @Override
    public List<Category> getByParentId(Long id) {
        return repository.findCategoriesByIdParentCate(id)
                .orElseThrow(() -> new NotFoundException(String.format("Not found category with id = %d", id)));
    }

    private boolean checkDuplicate(String name) {
        return repository.findByName(name).isEmpty();
    }
}