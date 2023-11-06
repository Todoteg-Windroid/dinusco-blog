package com.todoteg.repositories.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.todoteg.models.Article;
import com.todoteg.repositories.IArticleRepo;


@Repository
public class ArticleRepoImpl extends CRUDRepoImpl<Article, Long> implements IArticleRepo {

	public ArticleRepoImpl(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
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

	@Override
	protected Article mapRowToEntity(ResultSet resultSet, int rowNum) throws SQLException {
		Timestamp createDateTimestamp = resultSet.getTimestamp("create_date");
	    LocalDateTime createDate = createDateTimestamp.toLocalDateTime();
	    
	    Timestamp updateDateTimestamp = resultSet.getTimestamp("update_date");
	    LocalDateTime updateDate = updateDateTimestamp.toLocalDateTime();
		
        return Article.builder()
        		.id(resultSet.getLong("id"))
        		.author(resultSet.getLong("author"))
        		.authorUsername(resultSet.getString("username"))
        		.slug(resultSet.getString("slug"))
        		.title(resultSet.getString("title"))
        		.contentPreview(resultSet.getString("content_preview"))
        		.contentFull(resultSet.getString("content_full"))
        		.summary(resultSet.getString("summary"))
        		.createDate(createDate)
        		.updateDate(updateDate)
        		.enabled(resultSet.getBoolean("enabled"))
        		.build();
	}

}
