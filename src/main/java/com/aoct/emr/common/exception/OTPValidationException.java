package com.aoct.emr.common.exception;

public class OTPValidationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public OTPValidationException(String message)
	{
		super(message);
	}

}
