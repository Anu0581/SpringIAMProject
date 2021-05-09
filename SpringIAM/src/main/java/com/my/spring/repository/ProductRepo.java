package com.my.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.my.spring.entity.ProductEntity;

public interface ProductRepo extends JpaRepository<ProductEntity, Long> {

	ProductEntity findByProduct(String product);

}
