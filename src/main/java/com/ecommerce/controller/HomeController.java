package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommerce.global.GlobalData;
import com.ecommerce.service.CatogeryService;
import com.ecommerce.service.ProductService;

@Controller
public class HomeController {

	@Autowired
	CatogeryService catogeryService;
	
	@Autowired
	ProductService productService;
	
	@GetMapping({"/", "home"})
	public String home(@RequestParam(defaultValue = "none") String sortBy,
	                   @RequestParam(defaultValue = "asc") String order,
	                   Model model) {
	    model.addAttribute("cartCount", GlobalData.cart.size());
	    
	    // Add categories to model
	    model.addAttribute("categories", catogeryService.getAllCatogery());

	    // Add sorted products to model
	    model.addAttribute("products", productService.getAllProduct(sortBy, order));
	    
	    return "index";
	}

	
	@GetMapping("/shop")
	public String getAllCategoriesAndShop(@RequestParam(defaultValue = "none") String sortBy,
	                                      @RequestParam(defaultValue = "asc") String order, 
	                                      Model model) {
	    model.addAttribute("cartCount", GlobalData.cart.size());
	    model.addAttribute("categories", catogeryService.getAllCatogery());
	    model.addAttribute("products", productService.getAllProduct(sortBy, order));
	    return "shop";
	}

	
	@GetMapping("/shop/category/{id}")
	public String shopByCategory(Model model, @PathVariable int id ) {
		
		model.addAttribute("cartCount", GlobalData.cart.size());
		
		
		model.addAttribute("categories", catogeryService.getAllCatogery());
		model.addAttribute("products", productService.getAllProductByCatogeryId(id));
		return "shop";		
	}
	
	@GetMapping("/shop/viewproduct/{id}")
	public String viewProduct(Model model,@PathVariable int  id) {
	
		model.addAttribute("cartCount", GlobalData.cart.size());
		
		
		model.addAttribute("product", productService.getProductById(id).get());
		return "viewProduct";
		}
	
}
