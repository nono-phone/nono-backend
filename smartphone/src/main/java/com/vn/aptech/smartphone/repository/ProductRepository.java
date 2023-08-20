package com.vn.aptech.smartphone.repository;

import com.vn.aptech.smartphone.entity.Category;
import com.vn.aptech.smartphone.entity.Product;
import lombok.extern.java.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategories (Category category);
}
