package com.vn.aptech.smartphone.service.Impl;

import com.vn.aptech.smartphone.entity.Category;
import com.vn.aptech.smartphone.entity.Product;
import com.vn.aptech.smartphone.exception.ConflictException;
import com.vn.aptech.smartphone.exception.NotFoundException;
import com.vn.aptech.smartphone.repository.ProductRepository;
import com.vn.aptech.smartphone.service.CategoryService;
import com.vn.aptech.smartphone.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {


    private final CategoryService categoryService;

    private final ProductRepository productRepository;

    @Override
    public List<Product> get() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(Long id) {
        return findByIdProduct(id);
    }

    @Override
    public List<Product> getByCate(Long idCate) {
        return productRepository.findByCategories(categoryService.getById(idCate));
    }

    @Override
    public Product update(Product product, Long id) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    Optional.ofNullable(product.getName()).ifPresent(name -> {
                        if (!name.equals(existingProduct.getName())) {
                            checkDuplicate(name);
                            existingProduct.setName(name);
                        }
                    });

                    Optional.ofNullable(product.getPrice()).ifPresent(existingProduct::setPrice);
                    Optional.ofNullable(product.getDescription()).ifPresent(existingProduct::setDescription);
                    Optional.ofNullable(product.getImage()).ifPresent(existingProduct::setImage);
                    existingProduct.setEnable(product.isEnable());

                    Optional.ofNullable(product.getQuantity()).ifPresent(existingProduct::setQuantity);

                    Optional.ofNullable(product.getCategories()).ifPresent(category -> {
                        Category existingCategory = categoryService.getById(category.getId());
                        existingProduct.setCategories(existingCategory);
                    });

                    return productRepository.save(existingProduct);
                })
                .orElseThrow(() -> new NotFoundException(
                        String.format("Not found product with id = %d", id)));
    }

    @Override
    public void delete(Long id) {
        findByIdProduct(id);
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> add(List<Product> products) {
        List<Product> savedProducts = new ArrayList<>();
        for(Product product: products) {
            checkDuplicate(product.getName());
            Category category = categoryService.getById(product.getCategories().getId());
            Product savedProduct = productRepository.save(product);
            savedProduct.setCategories(category);
            savedProducts.add(savedProduct);
        }
        return savedProducts;
    }

    @Override
    public List<Product> findAllByStatus(boolean isEnable) {
        return productRepository.findAllByIsEnable(true);
    }

    private Product findByIdProduct(Long idProduct) {
        return productRepository.findById(idProduct).orElseThrow(
                () -> new NotFoundException(String.format("Not found product with id = %d", idProduct))
        );
    }

    private void checkDuplicate(String name) {
        if(productRepository.findByName(name).isPresent()) {
            throw new ConflictException("Duplicate category name");
        }
    }

}
