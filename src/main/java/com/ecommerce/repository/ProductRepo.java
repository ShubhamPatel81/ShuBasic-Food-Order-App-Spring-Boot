package com.ecommerce.repository;
import org.springframework.data.domain.Sort;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Product;

public interface ProductRepo extends JpaRepository<Product, Long>{
   
	List<Product> findAllByCategory_id(int id);
	  List<Product> findAll(Sort sort);
}
