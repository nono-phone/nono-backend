package com.vn.aptech.smartphone.service.Impl;

import com.vn.aptech.smartphone.entity.Category;
import com.vn.aptech.smartphone.repository.CategoryRepository;
import com.vn.aptech.smartphone.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {
    private final CategoryRepository repository;

    @Override
    public List<Category> get() {
        return repository.findAll();
    }

    @Override
    public Category add(Category categories) throws Exception {
        if (checkDuplicate(categories.getName())) {
            return repository.save(categories);
        } else {
            throw new Exception("Duplicate category name");
        }
    }

    @Override
    public Optional<Category> add1(Category categories) {
        return repository.findByName(categories.getName());
    }

    @Override
    public Category update() {
        return null;
    }

    @Override
    public void delete(Long id) {
        if (repository.findById(id).isPresent()) {
            repository.delete(getById(id).get());
        }
    }

    @Override
    public Optional<Category> getById(Long id) {
        return repository.findById(id);
    }

    private boolean checkDuplicate(String name) {
        return repository.findByName(name).isEmpty();
    }
}