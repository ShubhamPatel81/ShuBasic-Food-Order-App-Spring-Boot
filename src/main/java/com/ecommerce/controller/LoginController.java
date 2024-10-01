package com.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.ecommerce.global.GlobalData;
import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import com.ecommerce.repository.RoleRepo;
import com.ecommerce.repository.UserRepo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class LoginController {
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	RoleRepo roleRepo;
	
	@GetMapping("/login")
	public String login() {
		GlobalData.cart.clear();
		return "/login";
	}
	
	@GetMapping("/register")
	public String registerGet() {
		return "register";
	}
	
	@PostMapping("/register")
	public String registerpost(@ModelAttribute("user") User user, HttpServletRequest request) throws ServletException {
	    String email = user.getEmail();

	    // Check if the email already exists in the database
	    if (userRepo.findUserByEmail(email).isPresent()) {
	        // Handle case where email is already taken (show error message, redirect, etc.)
	        return "redirect:/register?error=email_exists";
	    }

	    String password = user.getPassword();
	    user.setPassword(bCryptPasswordEncoder.encode(password));

	    // Assign a role to the new user
	    List<Role> roles = new ArrayList<>();
	    roles.add(roleRepo.findById(2).get());
	    user.setRoles(roles);

	    // Save the new user to the database
	    userRepo.save(user);

	    // Programmatically log in the user
	    request.login(user.getEmail(), password);
	    return "redirect:/";
	}

	
	 
}
