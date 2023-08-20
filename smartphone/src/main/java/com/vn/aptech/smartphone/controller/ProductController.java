package com.vn.aptech.smartphone.controller;

import com.vn.aptech.smartphone.entity.Product;
import com.vn.aptech.smartphone.service.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> get(){
        return new ResponseEntity<>(productService.get(), OK);
    }

    @GetMapping("/")
    public Optional<Product> getById(@RequestParam Long id){
        return productService.getById(id);
    }

//    @PostMapping
//    public Product add(@RequestBody Product product){
//        return IProductService.add(product);
//    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody @Valid Product product) {
        return new ResponseEntity<>(productService.add(product), CREATED);
    }
    @GetMapping("/getByCate")
    public ResponseEntity<List<Product>> getByCate(@RequestParam Long id){
        return new ResponseEntity<>(productService.getByCate(id), OK);
    }
}