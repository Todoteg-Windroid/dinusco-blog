package com.todoteg.services.impl;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

//import com.todoteg.mappers.ArticleRowMapper;
import com.todoteg.models.Article;
import com.todoteg.repositories.impl.ArticleRepoImpl;
import com.todoteg.repositories.impl.CRUDRepoImpl;
import com.todoteg.security.SecurityUser;
import com.todoteg.services.IArticleService;

@Service
public class ArticleServiceImpl extends CRUDServiceImpl<Article, Long> implements IArticleService{

	@Autowired
	private ArticleRepoImpl repository;
	
	@Autowired
	private FileStorageService service;
	
	/*
	 * @Autowired private ArticleRowMapper mapper;
	 */
	
	@Override
	protected CRUDRepoImpl<Article, Long> getRepo() {
		return repository;
	}
    @Override
    public Article findBySlug(String id) {
    	return repository.findBySlug(id);
    }
	@Override
	public void editArticle(Article article) {
		article = setSlug(article);
		repository.update(article);
		
	}
	@Override
	public void createArticle(Article article) {
		article.setCreateDate(LocalDateTime.now());
		article = setSlug(article);
		Number newId = repository.save(article);
		
		if(newId != null) {
			article.setId(newId.longValue());
			service.fileSave(article);
		}
    }

	@Override
	public String findFirstImageArticle(String Content) {
		String regex = "\"insert\": \\{\"image\": \"(https?:\\/\\/[^\"]+|\\/[^\"]+)\"}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(Content);
        if (matcher.find()) {
            String urlImage = matcher.group(1);
            return urlImage;
            
        }
		return null;
		
	}
	public Article setSlug(Article article) {
        String slug = generateSlug(article.getTitle());
        article.setSlug(slug);
        
        return article;
	}
    
	public String generateSlug(String title) {
	        if (title == null) {
	            return "";
	        }
	        String cleanedTitle = title.replaceAll("[^a-zA-Z0-9\\s-]", "");
	        String slug = cleanedTitle.replaceAll("\\s+", "-");
	        return slug.toLowerCase();
	    }

}
