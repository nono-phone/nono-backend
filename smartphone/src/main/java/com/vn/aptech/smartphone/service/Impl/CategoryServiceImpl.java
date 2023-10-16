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
        checkDuplicate(category.getName());
        return repository.save(category);
    }

    @Override
    public Category update(Category category, Long id) {
        return repository.findById(id)
                .map(cate -> {
                    if (category.getName() != null) {
                        checkDuplicate(category.getName());
                        cate.setName(category.getName());
                    }
                    if (category.getIdParentCate() != null) {
                        cate.setIdParentCate(category.getIdParentCate());
                    }
                    if (category.getDescription() != null) {
                        cate.setDescription(category.getDescription());
                    }
                    if (category.getImage() != null) {
                        cate.setImage(category.getImage());
                    }
                    if (!category.isEnable()) {
                        cate.setEnable(category.isEnable());
                    }
                    return repository.save(cate);
                })
                .orElseThrow(() -> new NotFoundException(
                        String.format("Not found category with id = %d", id)));
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

    private void checkDuplicate(String name) {
        if(repository.findByName(name).isPresent()) {
            throw new ConflictException("Duplicate category name");
        }
    }
}