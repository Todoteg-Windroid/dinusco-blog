package com.todoteg.services.impl;

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
    	return repository.findBy(id);
    }
	public void createArticle(Article article) {
		String content = article.getContentFull();
		int previewMaxLength = 480;
		int summaryMaxLength = 160;
		String textWithoutHtml = content.replaceAll("<[^>]+>", "");
        String contentPreview = generatePreview(content, previewMaxLength);
        String summary = generatePreview(textWithoutHtml, summaryMaxLength);
        String slug = generateSlug(article.getTitle());
        
        article.setContentPreview(contentPreview);
        article.setSummary(summary);
        article.setSlug(slug);
        

        repository.save(article);
    }
    
	 public String generateSlug(String title) {
	        if (title == null) {
	            return "";
	        }
	        String cleanedTitle = title.replaceAll("[^a-zA-Z0-9\\s-]", "");
	        String slug = cleanedTitle.replaceAll("\\s+", "-");
	        return slug.toLowerCase();
	    }
	
    public String generatePreview(String content, int maxLength) {
        if (content.length() <= maxLength) {
            return content; 
        } else {
            return content.substring(0, maxLength) + "...";
        }
    }

}
