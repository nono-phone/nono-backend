package com.vn.aptech.smartphone.controller;

import com.vn.aptech.smartphone.entity.Product;
import com.vn.aptech.smartphone.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> get(){
        return new ResponseEntity<>(productService.get(), OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id){
        return new ResponseEntity<>(productService.getById(id), HttpStatus.OK);
    }

    @SecurityRequirement(name = "access_token")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> create(@RequestBody @Valid Product product) {
        return new ResponseEntity<>(productService.add(product), CREATED);
    }
    @GetMapping("/getByCate")
    public ResponseEntity<List<Product>> getByCate(@RequestParam Long id){
        return new ResponseEntity<>(productService.getByCate(id), OK);
    }

    @SecurityRequirement(name = "access_token")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable Long id) {
        return new ResponseEntity<>(productService.update(product, id), HttpStatus.OK);
    }
    @SecurityRequirement(name = "access_token")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}