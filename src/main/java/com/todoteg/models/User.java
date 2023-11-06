package com.todoteg.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
	private Long id;
    private String username;
    private String email;
    private String password;
    @Builder.Default private Role role = Role.USER;
    @Builder.Default private boolean enabled = true;
    
	public String getRole() {
		return role.name();
	}
}
