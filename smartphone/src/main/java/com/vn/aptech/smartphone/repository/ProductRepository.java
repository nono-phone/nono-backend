package com.vn.aptech.smartphone.repository;

import com.vn.aptech.smartphone.entity.Category;
import com.vn.aptech.smartphone.entity.Product;
import lombok.extern.java.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByName(String name);
    List<Product> findByCategories (Category category);

    List<Product> findAllByIsEnable (boolean isEnable);
}
