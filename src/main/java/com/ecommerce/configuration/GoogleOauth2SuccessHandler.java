package com.ecommerce.configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;

import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import com.ecommerce.repository.RoleRepo;
import com.ecommerce.repository.UserRepo;

@Component
public class GoogleOauth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, 
                                        HttpServletResponse response,
                                        Authentication authentication) 
                                        throws IOException, ServletException {

        // Get OAuth2 token
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        String email = token.getPrincipal().getAttributes().get("email").toString();

        // Check if user already exists
        if (!userRepo.findUserByEmail(email).isPresent()) {
            // If the user does not exist, create and save a new user
            User user = new User();
            user.setFirstName(token.getPrincipal().getAttributes().get("given_name").toString());
            user.setLastName(token.getPrincipal().getAttributes().get("family_name").toString()); // Correct key
            user.setEmail(email);

            // Assign the default role to the user
            List<Role> roles = new ArrayList<>();
            roles.add(roleRepo.findById(2).get()); // Assuming role with ID 2 is the default role
            user.setRoles(roles);

            // Save the new user
            userRepo.save(user);
        }

        // Redirect to the home page after successful login
        redirectStrategy.sendRedirect(request, response, "/");
    }
}
