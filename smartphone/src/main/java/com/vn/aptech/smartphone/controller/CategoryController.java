package com.vn.aptech.smartphone.controller;

import com.vn.aptech.smartphone.dto.response.UserResponseDto;
import com.vn.aptech.smartphone.entity.Category;
import com.vn.aptech.smartphone.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
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

    @Operation(summary = "Add a category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created category product details",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Category.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid category information format",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))}),
    })
    @SecurityRequirement(name = "access_token")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Category> add(@RequestBody Category category) throws Exception {
        return new ResponseEntity<>(categoryService.add(category), CREATED);
    }

    @GetMapping
    public ResponseEntity<Category> getById(@RequestParam Long id) {
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @SecurityRequirement(name = "access_token")
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