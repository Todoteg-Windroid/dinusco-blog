package com.todoteg.repositories.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;

import com.todoteg.models.Tag;
import com.todoteg.repositories.ITagRepo;

public class TagRepoImpl extends CRUDRepoImpl<Tag,Long> implements ITagRepo {

	public TagRepoImpl(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	@Override
	protected String getTableName() {
		return "tags";
	}

	@Override
	protected Tag mapRowToEntity(ResultSet resultSet, int rowNum) throws SQLException {
		return Tag.builder()
        		.id(resultSet.getLong("id"))
        		.name(resultSet.getString("name"))
        		.build();
	}

}
