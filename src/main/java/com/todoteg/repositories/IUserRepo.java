package com.todoteg.repositories;


import com.todoteg.models.User;

public interface IUserRepo extends ICRUDRepo<User, Long> {

	User findByUsername(String username);
}
