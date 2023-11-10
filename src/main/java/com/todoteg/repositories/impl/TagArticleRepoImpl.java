package com.todoteg.repositories.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.todoteg.models.TagArticle;
import com.todoteg.repositories.ITagArticleRepo;

public class TagArticleRepoImpl extends CRUDRepoImpl<TagArticle, Long> implements ITagArticleRepo{

	private final static BeanPropertyRowMapper<TagArticle> productoEntityRowMapper = BeanPropertyRowMapper.newInstance(TagArticle.class);

	public TagArticleRepoImpl(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate, productoEntityRowMapper);

	}
	@Override
	protected String getTableName() {
		return "tag_article";
	}

	

}
