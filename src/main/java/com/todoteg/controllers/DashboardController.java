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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.todoteg.models.Article;
import com.todoteg.models.User;
import com.todoteg.security.SecurityUser;
import com.todoteg.services.IArticleService;
import com.todoteg.services.IUserService;

@Controller
@RequestMapping({"/dashboard","/dashboard/"})
public class DashboardController {
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addCustomFormatter(new PGobjectFormatter());
    }
	
    @Autowired
    private IArticleService articleService;
    
    @Autowired
    private IUserService userService;
	
    @GetMapping
    public String viewArticlesAdmin(Model model) {
    	model.addAttribute("view", "ListAdmin.jsp");
        return "admin";
    }
    
    @GetMapping("/users")
    public String viewArticles(Model model) {
    	System.out.println("Usuario autenticado: " + SecurityContextHolder.getContext().getAuthentication().getName());
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        model.addAttribute("view", "ListUsersAdmin.jsp");
        return "admin";
    }
    
    @GetMapping("/create")
    public String createRoute(Model model) {
    	model.addAttribute("article", new Article());
    	model.addAttribute("view", "createArticle.jsp");
        return "admin";
    }
    
    @PostMapping("/create")
    public String createArticle(@ModelAttribute Article article, Principal principal ) {
    	SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
    	Long author = user.getId();
    	article.setAuthor(author);
    	articleService.createArticle(article);
    	return "redirect:/dashboard";
    }
    
    @GetMapping("/edit/{id}")
    public String editRoute(@PathVariable Long id, Model model) {
    	Article articleFound = articleService.findById(id);
    	String escapedContent = StringEscapeUtils.escapeEcmaScript(articleFound.getContentFull().getValue());
    	model.addAttribute("article", new Article());
    	model.addAttribute("title", articleFound.getTitle());
    	model.addAttribute("content", escapedContent);
    	model.addAttribute("view", "createArticle.jsp");
        return "admin";
    }
    
    @PostMapping("/edit/{id}")
    public String editArticle(@ModelAttribute Article article ) {
    	System.out.println("articleEdit "+ article);
    	articleService.editArticle(article);
    	return "redirect:/dashboard";
    }
    @GetMapping("/delete/{id}")
    public String deleteArticle(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    	if (articleService.delete(id)) {
            redirectAttributes.addFlashAttribute("message", "Delete Success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Delete Failure");
        }
    	return "redirect:/dashboard";
    	
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
                throw new ParseException("Error parsing String to PGobject", 0);
            }
        }

        @Override
        public String print(PGobject object, Locale locale) {
            return object.getValue();
        }
    }
}
