package com.aoct.emr.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aoct.emr.common.bl.AuthBl;
import com.aoct.emr.common.entity.RefreshToken;
import com.aoct.emr.common.uiRequest.JwtRequest;
import com.aoct.emr.common.uiResponse.JwtResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthBl authBl;

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {

		authBl.doAuthenticate(request.getEmail(), request.getPassword());

		UserDetails userDetails = authBl.loadUserByUsername(request.getEmail());
		String token = authBl.generateToken(userDetails);
		RefreshToken createRefreshToken = authBl.createRefreshToken(request.getEmail());

		JwtResponse response = JwtResponse.builder().token(token).refreshToken(createRefreshToken.getRefreshToken())
				.userName(userDetails.getUsername()).
				build();
		
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/forgotPassword")
	public String forgotPassword(@RequestBody JwtRequest request)
	{
		return authBl.forgotPassword(request);
	}



}
