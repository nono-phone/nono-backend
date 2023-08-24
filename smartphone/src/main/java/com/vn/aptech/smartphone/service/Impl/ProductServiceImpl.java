package com.vn.aptech.smartphone.service.Impl;

import com.vn.aptech.smartphone.entity.Product;
import com.vn.aptech.smartphone.repository.ProductRepository;
import com.vn.aptech.smartphone.service.ICategoryService;
import com.vn.aptech.smartphone.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;

    private final ICategoryService iCategoryService;

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
        return productRepository.findByCategories(iCategoryService.getById(idCate));
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
