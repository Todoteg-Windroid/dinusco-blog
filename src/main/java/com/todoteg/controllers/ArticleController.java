package com.todoteg.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.todoteg.models.Article;
import com.todoteg.security.SecurityUser;
import com.todoteg.services.IArticleService;

@Controller
public class ArticleController {
    
    @Autowired
    private IArticleService articleService;
    
    @GetMapping("/articles")
    public String viewArticles(Model model) {
    	System.out.println("Usuario autenticado: " + SecurityContextHolder.getContext().getAuthentication().getName());
        List<Article> articles = articleService.findAll();
        model.addAttribute("articles", articles);
        return "inicio";
    }
    
    @GetMapping("/articles/{slug}")
    public String viewArticle(@PathVariable String slug, Model model) {
        Article article = articleService.findBySlug(slug);
        model.addAttribute("article", article);
        return "viewArticle";
    }
    
    @GetMapping("/createArticle")
    public String signupRoute(Model model) {
    	model.addAttribute("article", new Article());
        return "createArticle";
    }
    
    @PostMapping("/createArticle")
    public String signup(@ModelAttribute Article article ) {
    	SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Long author = user.getId();
    	article.setAuthor(author);
    	articleService.createArticle(article);
    	return "redirect:/articles";
    }
}
