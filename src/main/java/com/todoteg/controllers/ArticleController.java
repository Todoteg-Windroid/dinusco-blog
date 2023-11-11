package com.todoteg.controllers;
import java.security.Principal;
import java.util.List;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todoteg.models.Article;
import com.todoteg.services.IArticleService;

@Controller
@RequestMapping({"/articles","/articles/"})
public class ArticleController {
	
	
    @Autowired
    private IArticleService articleService;
    
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Article>> getArticles() {
        List<Article> articles = articleService.findAll();
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }
    
    @GetMapping("/list")
    public String viewArticles() {
        return "inicio";
    }
    
    @GetMapping("/{slug}")
    public String viewArticle(@PathVariable String slug, Model model, Principal principal) {
        Article article = articleService.findBySlug(slug);
        String content = article.getContentPreview().toString();
        if(principal != null) {
        	content = article.getContentFull().getValue();
        }
        String escapedContent = StringEscapeUtils.escapeEcmaScript(content);
        model.addAttribute("title", article.getTitle());
        model.addAttribute("author", article.getAuthorUsername());
        model.addAttribute("createDate", article.getCreateDate());
        model.addAttribute("content", escapedContent);
        return "viewArticle";
    }
   
    
}
