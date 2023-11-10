package com.todoteg.repositories.impl;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.todoteg.models.Article;
import com.todoteg.repositories.IArticleRepo;


@Repository
public class ArticleRepoImpl extends CRUDRepoImpl<Article, Long> implements IArticleRepo {



	public ArticleRepoImpl(JdbcTemplate jdbcTemplate, RowMapper<Article> rowMapper) {
		super(jdbcTemplate, rowMapper);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getTableName() {
		return "articles";
	}
	
	@Override
    protected String generateSelectAllSql() {
        return "SELECT articles.*, users.username\r\n"
    			+ "    	FROM articles\r\n"
    			+ "    	INNER JOIN users ON articles.author = users.id"
    			+ "		ORDER BY create_date DESC";
    }
	
    @Override
    public Article findBySlug(String slug) {
	    	String query = "SELECT *, users.username\r\n"
	    			+ "    	FROM articles\r\n"
	    			+ "    	INNER JOIN users ON articles.author = users.id\r\n"
	    			+ "    	WHERE slug = :id";
	    	return this.findBy(slug, query);
    }


}
