package com.aoct.emr.common.bl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.aoct.emr.common.entity.RefreshToken;
import com.aoct.emr.common.entity.UserModel;
import com.aoct.emr.common.service.AuthService;
import com.aoct.emr.common.service.RefreshTokenService;
import com.aoct.emr.common.uiRequest.JwtRequest;
import com.aoct.emr.common.utility.TokenGenerator;

@Component
public class AuthBl {

	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private AuthService service;
	
	@Autowired
	TokenGenerator tokenGen;
	
	@Autowired
	RefreshTokenService refreshService;
	
	@Autowired
	PasswordEncoder encoder;

	public void doAuthenticate(String email, String password) {

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
		try {
			manager.authenticate(authentication);

		} catch (BadCredentialsException e) {
			throw new BadCredentialsException(" Invalid Username or Password  !!");
		}

	}

	public UserDetails loadUserByUsername(String email) {
		// TODO Auto-generated method stub
		return service.loadUserByUsername(email);
	
	}

	public String generateToken(UserDetails userDetails) {
		
		return tokenGen.generateToken(userDetails);
	}

	public RefreshToken createRefreshToken(String email) {
		// TODO Auto-generated method stub
		return refreshService.createRefreshToken(email);
	}

	public String forgotPassword(JwtRequest request) {
		// TODO Auto-generated method stub
		UserModel model = new UserModel();
		model.setEmail(request.getEmail());
		model.setPassword(encoder.encode(request.getNewPassword()));

		return service.forgotPassword(model);	
	}

}
