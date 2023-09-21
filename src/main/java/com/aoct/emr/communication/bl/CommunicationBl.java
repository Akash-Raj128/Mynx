package com.aoct.emr.communication.bl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aoct.emr.common.exception.OTPValidationException;
import com.aoct.emr.communication.response.OtpUiResponse;
import com.aoct.emr.communication.service.SmsService;
import com.aoct.emr.communication.uiRequest.OtpUiRequest;
import com.aoct.emr.communication.uiRequest.OtpValidationRequest;

@Component
public class CommunicationBl {

	@Autowired
	SmsService service;
	Map<String, String> otpMap = new HashMap<>();

	public OtpUiResponse sendSMS(OtpUiRequest otpRequest) {
		// TODO Auto-generated method stub
		return service.sendSMS(otpRequest);
	}

	public String validateOtp(OtpValidationRequest otpValidationRequest) {
		// TODO Auto-generated method stub
		String otp = otpValidationRequest.getOtpNumber();
		String email = otpValidationRequest.getUsername();
		if (otp.equals(otpMap.get(email)))
			return "Otp Validated Successfully";
		else
			throw new OTPValidationException("OTP validation failed. Please enter a valid OTP.");

	}

	public String sendSimpleMail(EmailUiRequest request) {
		// TODO Auto-generated method stub
		return service.sendSimpleMail(request);
	}

	public String sendOtpMail(String mailId) {
		// TODO Auto-generated method stub
		String otp = service.generateOTP();
		String otpMessage = "Dear Customer , Your OTP is  " + otp
				+ " for sending sms through Spring boot application. Thank You.";
		otpMap.put(mailId, otp);
		EmailUiRequest req = new EmailUiRequest();
		req.setSubject("OTP for password reset");
		req.setMsgBody(otpMessage);
		req.setRecipient(mailId);
		return service.sendSimpleMail(req);

	}

}
