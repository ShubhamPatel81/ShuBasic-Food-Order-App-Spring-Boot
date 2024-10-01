package com.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepo;
import com.paytm.miniapp.utils.StringUtil;

@Service
public class ProductService {

	@Autowired
	ProductRepo productRepo;
	


    public List<Product> getAllProduct(String sortBy, String order) {
        boolean isAscending = StringUtils.hasText(order) && order.equalsIgnoreCase("asc");

        // Default sorting to name if no valid sortBy value is provided
        Sort sort;
        if ("sortByName".equalsIgnoreCase(sortBy)) {
            sort = Sort.by(isAscending ? Sort.Direction.ASC : Sort.Direction.DESC, "name");
        } 
        
        else if ("sortByDate".equalsIgnoreCase(sortBy)) {
            sort = Sort.by(isAscending ? Sort.Direction.ASC : Sort.Direction.DESC, "date");
        } 
        
        else {
            // Default sorting if "none" or invalid sortBy value is provided
            sort = Sort.unsorted();
        }

        return productRepo.findAll(sort);
    }

    
    
    
	
	public void addProduct(Product product) {
		productRepo.save(product);
	}
	
	public void removeProductById(long id ) {
		productRepo.deleteById(id);
	}
	
	public Optional<Product> getProductById(long id){
		return productRepo.findById(id);
	}
	
	public List<Product> getAllProductByCatogeryId(int id){
		return productRepo.findAllByCategory_id(id);
	}
}
