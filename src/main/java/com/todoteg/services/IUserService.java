package com.todoteg.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.todoteg.models.User;

public interface IUserService extends ICRUD<User, Long>, UserDetailsService{

}
