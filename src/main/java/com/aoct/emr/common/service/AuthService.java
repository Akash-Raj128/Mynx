package com.aoct.emr.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

	@Autowired
	private UserDetailsService userDetailsService;

	public UserDetails loadUserByUsername(String email) {
		// TODO Auto-generated method stub
		return userDetailsService.loadUserByUsername(email);

	}

}