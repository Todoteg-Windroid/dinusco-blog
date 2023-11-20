package com.todoteg.services;

import com.todoteg.models.Article;

public interface IArticleService extends ICRUD<Article, Long> {

	void createArticle(Article article);
	void editArticle(Article article);
	Article findBySlug(String id);
	String findFirstImageArticle(String Content);
}
