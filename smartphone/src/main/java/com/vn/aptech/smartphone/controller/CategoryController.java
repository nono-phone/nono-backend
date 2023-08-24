package com.vn.aptech.smartphone.controller;

import com.vn.aptech.smartphone.entity.Category;
import com.vn.aptech.smartphone.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final ICategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<List<Category>> get() {
        return ResponseEntity.ok(categoryService.get());
    }

    @PostMapping
    public ResponseEntity<Category> add(@RequestBody Category category) throws Exception {
        return new ResponseEntity<>(categoryService.add(category), CREATED);
    }

    @GetMapping
    public ResponseEntity<Category> getById(@RequestParam Long id) {
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @DeleteMapping
    public void deleteCate(@RequestParam Long id) {
        categoryService.delete(id);
    }

    @GetMapping(value = "/category-by-parent")
    public ResponseEntity<List<Category>> getByIdParent(@RequestParam Long idParent) {
        return ResponseEntity.ok(categoryService.getByParentId(idParent));
    }

}