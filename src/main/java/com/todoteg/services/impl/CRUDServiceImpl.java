package com.todoteg.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.todoteg.repositories.impl.CRUDRepoImpl;
import com.todoteg.services.ICRUD;

@Service
public abstract  class CRUDServiceImpl<T,ID> implements ICRUD<T, ID>  {

	protected abstract CRUDRepoImpl<T, ID> getRepo();
	@Override
	public List<T> findAll() {
		return getRepo().findAll();
	}

	@Override
	public T findById(ID id) {
		return getRepo().findById(id);
	}

	@Override
	public void save(T t) throws Exception {
		getRepo().save(t) ;
		
	}

	@Override
	public void update(T t) {
		getRepo().update(t) ;
		
	}

	@Override
	public boolean delete(ID id) {
		int rowAffected = getRepo().delete(id);
		if(rowAffected == 1) {
			return true;
		}
		return false;
	}

}
