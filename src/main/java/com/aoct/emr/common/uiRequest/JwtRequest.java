package com.aoct.emr.common.uiRequest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtRequest {
	
	private String email;
	private String password;
	private String newPassword;

}