package com.vn.aptech.smartphone.controller;

import com.vn.aptech.smartphone.entity.Category;
import com.vn.aptech.smartphone.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final ICategoryService categoryService;

    @GetMapping("/")
    public List<Category> get(){
        return categoryService.get();
    }

    @PostMapping
    public Category add(@RequestBody Category category) throws Exception {
        return categoryService.add(category);
    }

    @GetMapping
    public Optional<Category> getById(@RequestParam Long id){
        return categoryService.getById(id);
    }

    @DeleteMapping
    public void deleteCate(@RequestParam Long id){
        categoryService.delete(id);
    }
}