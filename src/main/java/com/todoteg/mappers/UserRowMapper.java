package com.todoteg.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.todoteg.models.Role;
import com.todoteg.models.User;

@Component
public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
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
