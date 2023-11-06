package com.todoteg.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.todoteg.models.User;
import com.todoteg.services.IUserService;


@Controller
public class UserController {
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    public AuthenticationProvider authProvider;

    @ModelAttribute("user")
    public User user() {
        return new User();
    }
    
	@GetMapping("/login")
	String loginRoute() {
		return "signup";
	}
    
    @GetMapping("/signup")
    public String signupRoute() {
        return "signup";
    }
    
    
    @PostMapping("/signup")
    public String signup(@ModelAttribute User user ) {
    	user.setPassword(passwordEncoder.encode(user.getPassword()));
    	try {
			userService.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}

    	
    	return "forward:/login";
    }
    
    @GetMapping("/user/{id}")
    public String viewArticle(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user";
    }
    
    // Otros métodos para crear, editar y eliminar artículos
}
