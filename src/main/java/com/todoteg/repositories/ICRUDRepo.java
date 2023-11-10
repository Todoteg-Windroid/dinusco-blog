package com.todoteg.repositories;

import java.sql.SQLException;
import java.util.List;

public interface ICRUDRepo<T,ID> {
	
	List<T> findAll();
    T findById(ID id);
    void save(T t) throws SQLException;
    void update(T t);
    void delete(ID id);
}
