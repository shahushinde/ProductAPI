package com.productApi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.productApi.entity.Product;

public interface ProductDao extends JpaRepository<Product, Long> {

}
