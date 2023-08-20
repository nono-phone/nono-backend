package com.vn.aptech.smartphone.service;

import com.vn.aptech.smartphone.entity.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    List<Product> get();
    Optional<Product> getById(Long id);
    List<Product> getByCate(Long idCate);
    Product update(Product product);
    void hiddenProduct(Long id);
    Product add(Product product);
}
