package com.todoteg.services.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todoteg.models.Article;
import com.todoteg.repositories.impl.ArticleRepoImpl;
import com.todoteg.repositories.impl.CRUDRepoImpl;
import com.todoteg.services.IArticleService;

@Service
public class ArticleServiceImpl extends CRUDServiceImpl<Article, Long> implements IArticleService{

	@Autowired
	private ArticleRepoImpl repository;
	
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
        repository.save(article);
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
