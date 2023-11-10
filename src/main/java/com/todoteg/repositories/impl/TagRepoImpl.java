package com.todoteg.repositories.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.todoteg.models.Tag;

import com.todoteg.repositories.ITagRepo;

public class TagRepoImpl extends CRUDRepoImpl<Tag,Long> implements ITagRepo {

	private final static BeanPropertyRowMapper<Tag> rowMapper = BeanPropertyRowMapper.newInstance(Tag.class);

	public TagRepoImpl(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate, rowMapper);
		
	}

	@Override
	protected String getTableName() {
		return "tags";
	}



}
