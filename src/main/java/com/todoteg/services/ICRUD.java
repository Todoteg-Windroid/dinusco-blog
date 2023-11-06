package com.todoteg.services;

import java.util.List;

public interface ICRUD<T,ID> {
	
	List<T> findAll();
    T findById(ID id);
    void save(T t) throws Exception;
    void update(T t);
    void delete(ID id);
}
