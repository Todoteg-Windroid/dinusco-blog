package com.todoteg.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.todoteg.services.IUserService;

import lombok.RequiredArgsConstructor;


//import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	@Autowired
	private IUserService detailService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
    	MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
        return http
        		.authorizeHttpRequests(authRequest ->
                authRequest
                  .requestMatchers(mvcMatcherBuilder.pattern("/articles/create")).authenticated()
                  .anyRequest().permitAll()
                  )
        		.formLogin(form->form.loginPage("/login").permitAll().loginProcessingUrl("/login").defaultSuccessUrl("/articles"))
        		.csrf(csrf->csrf.disable())
        		.logout(out ->
                out.logoutRequestMatcher(mvcMatcherBuilder.pattern("/logout"))
		                .permitAll()
		                )
		        .authenticationProvider(authProvider())
        		.build();
            
            
    }
    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(detailService);
                
                return provider;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }

}