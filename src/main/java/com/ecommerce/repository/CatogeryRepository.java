package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Category;

public interface CatogeryRepository extends JpaRepository<Category, Integer>{

}
