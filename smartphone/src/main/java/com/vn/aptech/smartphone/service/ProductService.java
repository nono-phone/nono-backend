package com.vn.aptech.smartphone.service;

import com.vn.aptech.smartphone.controller.ProductController;
import com.vn.aptech.smartphone.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> get();
    Product getById(Long id);
    List<Product> getByCate(Long idCate);
    Product update(Product product,Long idProduct);
    void delete(Long id);
    List<Product> add(List<Product> product);
    List<Product> findAllByStatus(boolean isEnable);
}
