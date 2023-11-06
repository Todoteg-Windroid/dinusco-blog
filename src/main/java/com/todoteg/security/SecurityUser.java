package com.todoteg.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.todoteg.models.User;

public class SecurityUser implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	private User user;

	public SecurityUser(User user) {
		this.user = user;
	}
	public Long getId() {
		return user.getId();
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		GrantedAuthority role = new SimpleGrantedAuthority(user.getRole());
		return List.of(role);
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		return user.isEnabled();
	}

}
