package com.todoteg.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticController {
    @GetMapping("/home")
    public String home(Model model) {
    	model.addAttribute("view", "mision.jsp");
        return "blogTemplate";
    }
    @GetMapping("/abaut-us")
    public String us(Model model) {
    	model.addAttribute("view", "abaut.jsp");
        return "blogTemplate";
    }
    @GetMapping("/contact")
    public String contact(Model model) {
    	model.addAttribute("view", "contact.jsp");
        return "blogTemplate";
    }
}

