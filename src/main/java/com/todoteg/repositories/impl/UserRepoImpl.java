package com.todoteg.repositories.impl;

//import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import com.todoteg.models.User;
import com.todoteg.repositories.IUserRepo;

@Repository
public class UserRepoImpl extends CRUDRepoImpl<User, Long> implements IUserRepo {

	//private final static BeanPropertyRowMapper<User> rowMapper = BeanPropertyRowMapper.newInstance(User.class);
	
	public UserRepoImpl(JdbcTemplate jdbcTemplate, RowMapper<User> rowMapper) {
		super(jdbcTemplate, rowMapper);
		
	}
	@Override
	protected String getTableName() {
		// TODO Auto-generated method stub
		return "users";
	}
	@Override
	public User findByUsername(String param) {
    	String query = "SELECT * FROM " + getTableName() + " WHERE username = :id";
    	return this.findBy(param,query);
    }
    
    
}