package com.vn.aptech.smartphone.service.Impl;

import com.vn.aptech.smartphone.entity.Product;
import com.vn.aptech.smartphone.repository.ProductRepository;
import com.vn.aptech.smartphone.service.CategoryService;
import com.vn.aptech.smartphone.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final CategoryService categoryService;

    @Override
    public List<Product> get() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> getByCate(Long idCate) {
        return productRepository.findByCategories(categoryService.getById(idCate));
    }

    @Override
    public Product update(Product product) {
        return null;
    }

    @Override
    public void hiddenProduct(Long id) {

    }

    @Override
    public Product add(Product product) {
        return productRepository.save(product);
    }
}
