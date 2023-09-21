package com.aoct.emr.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import com.aoct.emr.common.exception.UnauthenticUserHandler;
import com.aoct.emr.common.utility.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

	@Autowired
	private UnauthenticUserHandler point;
	@Autowired
	private JwtAuthenticationFilter filter;

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/auth/**").permitAll()
		    .requestMatchers("/communicate/**").permitAll()	       
	            .requestMatchers("/api/**").authenticated()
	            .anyRequest().authenticated()
	        )
	        .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
	        .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
	    return http.build();
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder);
		return provider;

	}
}
