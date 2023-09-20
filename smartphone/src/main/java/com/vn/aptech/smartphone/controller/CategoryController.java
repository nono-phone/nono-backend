package com.vn.aptech.smartphone.controller;

import com.vn.aptech.smartphone.entity.Category;
import com.vn.aptech.smartphone.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<List<Category>> get() {
        return ResponseEntity.ok(categoryService.get());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Category> add(@RequestBody Category category) throws Exception {
        return new ResponseEntity<>(categoryService.add(category), CREATED);
    }

    @GetMapping
    public ResponseEntity<Category> getById(@RequestParam Long id) {
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCate(@RequestParam Long id) {
        categoryService.delete(id);
    }

    @GetMapping(value = "/category-by-parent")
    public ResponseEntity<List<Category>> getByIdParent(@RequestParam Long idParent) {
        return ResponseEntity.ok(categoryService.getByParentId(idParent));
    }

}