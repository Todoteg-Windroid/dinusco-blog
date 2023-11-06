package com.todoteg.repositories.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.todoteg.models.Role;
import com.todoteg.models.User;
import com.todoteg.repositories.IUserRepo;

@Repository
public class UserRepoImpl extends CRUDRepoImpl<User, Long> implements IUserRepo {

	public UserRepoImpl(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getTableName() {
		// TODO Auto-generated method stub
		return "users";
	}

	@Override
	protected User mapRowToEntity(ResultSet resultSet, int rowNum) throws SQLException {
		String role = resultSet.getString("role");
        return User.builder()
        		.id(resultSet.getLong("id"))
        		.username(resultSet.getString("username"))
        		.email(resultSet.getString("email"))
        		.password(resultSet.getString("password"))
        		.role(this.getRole(role))
        		.enabled(resultSet.getBoolean("enabled"))
        		.build();
	}
	@Override
	public User findByUsername(String param) {
    	String query = "SELECT * FROM " + getTableName() + " WHERE username = :id";
    	return this.findBy(param,query);
    }
    
    public Role getRole(String role) {
        try {
            return Role.valueOf(role.toUpperCase()); // Convierte a mayúsculas para ser insensible a mayúsculas/minúsculas
        } catch (IllegalArgumentException e) {
            // La cadena no representa un valor válido del enum
            return null;
        }
    }
}