package com.todoteg.repositories;


import java.util.List;

import com.todoteg.models.User;

public interface IUserRepo  {

	List<User> findAll();

	User findByUsername(String username);

	User findById(Long id);

	void save(User user);

	void update(User user);

	void delete(Long id);
}
