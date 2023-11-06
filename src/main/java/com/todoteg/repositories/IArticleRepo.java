package com.todoteg.repositories;

import com.todoteg.models.Article;

public interface IArticleRepo extends ICRUDRepo<Article, Long> {

	Article findBySlug(String id);

}
