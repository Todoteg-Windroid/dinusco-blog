package com.todoteg.controllers;
import java.security.Principal;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import org.apache.commons.text.StringEscapeUtils;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todoteg.models.Article;
import com.todoteg.security.SecurityUser;
import com.todoteg.services.IArticleService;

@Controller
@RequestMapping("/articles")
public class ArticleController {
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addCustomFormatter(new PGobjectFormatter());
    }
	
    @Autowired
    private IArticleService articleService;
    
    @GetMapping
    public String viewArticles(Model model) {
    	System.out.println("Usuario autenticado: " + SecurityContextHolder.getContext().getAuthentication());
        List<Article> articles = articleService.findAll();
        model.addAttribute("articles", articles);
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
    
    @GetMapping("/create")
    public String createRoute(Model model) {
    	model.addAttribute("article", new Article());
        return "createArticle";
    }
    
    @PostMapping("/create")
    public String createArticle(@ModelAttribute Article article, Principal principal ) {
    	SecurityUser user = (SecurityUser) principal;
    	Long author = user.getId();
    	article.setAuthor(author);
    	articleService.createArticle(article);
    	return "redirect:/articles";
    }
    
    @GetMapping("/edit/{id}")
    public String editRoute(@PathVariable Long id, Model model) {
    	Article articleFound = articleService.findById(id);
    	String escapedContent = StringEscapeUtils.escapeEcmaScript(articleFound.getContentFull().getValue());
    	model.addAttribute("article", new Article());
    	model.addAttribute("title", articleFound.getTitle());
    	model.addAttribute("content", escapedContent);
        return "createArticle";
    }
    
    @PostMapping("/edit/{id}")
    public String editArticle(@ModelAttribute Article article ) {
    	System.out.println("articleEdit "+ article);
    	articleService.editArticle(article);
    	return "redirect:/articles";
    }
    
    private static class PGobjectFormatter  implements Formatter<PGobject> {

        @Override
        public PGobject parse(String text, Locale locale) throws ParseException {
            try {
            	PGobject pgObject = new PGobject();
                pgObject.setType("jsonb");
                pgObject.setValue(text);
                return pgObject;
            } catch (Exception e) {
                throw new ParseException("Error parsing String to JsonNode", 0);
            }
        }

        @Override
        public String print(PGobject object, Locale locale) {
            return object.getValue();
        }
    }
}
