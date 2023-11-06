package com.todoteg.repositories.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.todoteg.models.Role;
import com.todoteg.models.User;
import com.todoteg.repositories.IUserRepo;

@Repository
public class UserRepoImpl implements IUserRepo {
	private final JdbcTemplate jdbcTemplate;

    public UserRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM users", this::mapRowToUser);
    }

    @Override
    public User findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM user WHERE id = ?", this::mapRowToUser, id);
    }
    @Override
	public User findByUsername(String username) {
		String query = "SELECT * FROM users WHERE username = ?";
		return jdbcTemplate.queryForObject(query, this::mapRowToUser, username);
	}
    @Override
    public void save(User user) {
    	Object[] property = {user.getUsername(), user.getEmail(), user.getPassword(), user.getRole(), user.isEnabled()};
        jdbcTemplate.update("INSERT INTO users (username, email, password, role, enabled) VALUES (?, ?, ?, ?, ?)", property);
    }

    @Override
    public void update(User user) {
    	Object[] property = {user.getUsername(), user.getEmail(), user.getPassword(), user.getRole(), user.isEnabled(), user.getId()};
        jdbcTemplate.update("UPDATE users SET username = ?, email = ?, password = ?, role = ?, enabled = ?  WHERE id = ?", property);
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM users WHERE id = ?", id);
    }

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
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
    
    public Role getRole(String role) {
        try {
            return Role.valueOf(role.toUpperCase()); // Convierte a mayúsculas para ser insensible a mayúsculas/minúsculas
        } catch (IllegalArgumentException e) {
            // La cadena no representa un valor válido del enum
            return null;
        }
    }
}