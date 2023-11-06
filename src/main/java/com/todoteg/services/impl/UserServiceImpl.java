package com.todoteg.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.todoteg.models.User;
import com.todoteg.repositories.impl.CRUDRepoImpl;
import com.todoteg.repositories.impl.UserRepoImpl;
import com.todoteg.security.SecurityUser;
import com.todoteg.services.IUserService;

@Service
public class UserServiceImpl extends CRUDServiceImpl<User, Long> implements IUserService{

	@Autowired
	private UserRepoImpl repository;
	
	@Override
	protected CRUDRepoImpl<User, Long> getRepo() {
		return repository;
	}
	
	@Override
	public void save(User user) throws Exception {
		
		try {
			repository.findByUsername(user.getUsername());
			throw new Exception("User found: " + user.getUsername());
			
		}catch(EmptyResultDataAccessException e) {
			repository.save(user);
		}
		
		
		
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = repository.findByUsername(username);
		if (user != null) {
			return new SecurityUser(user);			
		}
		throw new UsernameNotFoundException("User not found: " + username);
			
	}

}
