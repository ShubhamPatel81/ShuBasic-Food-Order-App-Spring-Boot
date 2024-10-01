package com.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.model.Category;
import com.ecommerce.repository.CatogeryRepository;

@Service
public class CatogeryService {
	
	@Autowired
	CatogeryRepository catogeryRepository;
	
	public void addCatogery(Category category ) {
			catogeryRepository.save(category);
	}
	
	public List<Category> getAllCatogery(){
		return catogeryRepository.findAll();
	}
	
	public void removeCategoryById(int id) {
	    catogeryRepository.deleteById(id);// No need to return anything
	}

	public Optional<Category> getCategoryById(int id) {
		return catogeryRepository.findById(id);
	}

}
