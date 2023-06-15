package com.spring.jpa.chap01_basic.entity.repository;

import com.spring.jpa.chap01_basic.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<Entity Type, Primary Key Type>
public interface ProductRepository extends JpaRepository<Product, Long> {
}

//ctrl + shift + T