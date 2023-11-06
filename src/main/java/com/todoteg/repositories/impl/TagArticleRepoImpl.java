package com.todoteg.repositories.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;

import com.todoteg.models.TagArticle;
import com.todoteg.repositories.ITagArticleRepo;

public class TagArticleRepoImpl extends CRUDRepoImpl<TagArticle, Long> implements ITagArticleRepo{

	public TagArticleRepoImpl(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	@Override
	protected String getTableName() {
		return "tag_article";
	}

	@Override
	protected TagArticle mapRowToEntity(ResultSet resultSet, int rowNum) throws SQLException {
		return TagArticle.builder()
        		.id(resultSet.getLong("id"))
        		.idTag(resultSet.getLong("idTag"))
        		.idArticle(resultSet.getLong("idArticle"))
        		.build();
	}

}
