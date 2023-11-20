package com.todoteg.repositories.impl;

import java.lang.reflect.Field;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.util.Assert;

import com.todoteg.repositories.ICRUDRepo;


public abstract class CRUDRepoImpl<T,ID> implements ICRUDRepo<T, ID>  {

	protected final JdbcTemplate jdbcTemplate;

	protected final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	protected final SimpleJdbcInsertOperations simpleJdbcInsert;
	
	private final RowMapper<T> rowMapper;

    public CRUDRepoImpl(JdbcTemplate jdbcTemplate, RowMapper<T> rowMapper) {
    	Assert.notNull(jdbcTemplate, "NamedParameterJdbcTemplate must not be null");
        Assert.notNull(rowMapper, "RowMapper must not be null");
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		this.rowMapper = rowMapper;
		this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(getTableName())
                .usingGeneratedKeyColumns("id"); // Assuming "id" is the generated key column
    }
    
    protected abstract String getTableName();
    
    protected String generateSelectAllSql() {
        return "SELECT * FROM " + getTableName();
    }

    protected String generateSelectBySql() {
        return "SELECT * FROM " + getTableName() + " WHERE id = :id";
    }
    
    @Override
    public List<T> findAll() {
    	try {
    	String query = generateSelectAllSql();
	    	return jdbcTemplate.query(query, rowMapper);
	    }catch (EmptyResultDataAccessException ex) {
	        return null;
	    }
    }
    
    @Override
    public T findById(ID id) {
    	String query = generateSelectBySql();
    	return findBy(id,query);
    }
    protected T findBy(Object param, String query) {
    	if(!(param instanceof Long || param instanceof String)) {
	    	return null;
    	}

        MapSqlParameterSource parameters = new MapSqlParameterSource("id", param);
    	try {
    		return namedParameterJdbcTemplate.queryForObject(query, parameters, rowMapper);	
    	}catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Number save(T entity) {
    	BeanPropertySqlParameterSource beanParams = new BeanPropertySqlParameterSource(entity);
    	//MapSqlParameterSource params = new MapSqlParameterSource();
        Number newId = simpleJdbcInsert.executeAndReturnKey(beanParams);
        if (newId != null) {
            return newId;
        }
        return null;
    }

    @Override
    public void update(T entity) {
        String tableName = getTableName();
        // Obtener todos los campos de la entidad utilizando reflexión
        Field[] fields = entity.getClass().getDeclaredFields();
        StringBuilder setSql = new StringBuilder("UPDATE " + tableName + " SET ");
        // Crear un objeto para mantener los parámetros y sus valores
        MapSqlParameterSource parameters = new MapSqlParameterSource();

        for (Field field : fields) {
            field.setAccessible(true); // Hacer que el campo sea accesible
            String fieldName = field.getName();

            try {
                Object value = field.get(entity);
                if (value != null) {
                    // Agregar el nombre del campo y su valor a la parte SET de la consulta SQL
                    setSql.append(camelToSnake(fieldName)).append(" = :").append(fieldName).append(", ");
                    parameters.addValue(fieldName, value);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error al acceder al campo: " + fieldName, e);
            }
        }

        if (parameters.getValues().isEmpty()) {
            throw new IllegalArgumentException("No se proporcionaron valores para actualizar.");
        }

        // Eliminar la última coma y espacio en la cadena SET
        setSql.setLength(setSql.length() - 2);
        setSql.append(" WHERE id = :id");

        namedParameterJdbcTemplate.update(setSql.toString(), parameters);
    }

    @Override
    public int delete(ID id) {
    	String query = "DELETE FROM " + getTableName() + " WHERE id = :id";
        MapSqlParameterSource parameters = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.update(query, parameters);
    }
    
    public static String camelToSnake(String str){
        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";
        return str.replaceAll(regex, replacement) .toLowerCase();
    }
	
}
