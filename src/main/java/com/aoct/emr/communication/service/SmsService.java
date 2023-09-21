package com.aoct.emr.communication.service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.aoct.emr.communication.bl.EmailUiRequest;
import com.aoct.emr.communication.config.TwilioConfig;
import com.aoct.emr.communication.response.OtpUiResponse;
import com.aoct.emr.communication.uiRequest.OtpUiRequest;
import com.aoct.emr.communication.uiRequest.OtpValidationRequest;
import com.aoct.emr.communication.utility.OtpStatus;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SmsService {

	@Autowired
	private TwilioConfig twilioConfig;
	
	@Autowired
	private JavaMailSender javaMailSender;
	 
    @Value("${spring.mail.username}") private String sender;
	
	
	Map<String, String> otpMap = new HashMap<>();

	public OtpUiResponse sendSMS(OtpUiRequest otpRequest) {
		OtpUiResponse otpResponseDto = null;
		try {
			PhoneNumber to = new PhoneNumber(otpRequest.getPhoneNumber());// to
			PhoneNumber from = new PhoneNumber(twilioConfig.getPhoneNumber()); // from
			String otp = generateOTP();
			String otpMessage = "Dear Customer , Your OTP is  " + otp
					+ " for sending sms through Spring boot application. Thank You.";
			Message message = Message.creator(to, from, otpMessage).create();
			otpMap.put(otpRequest.getUsername(), otp);
			otpResponseDto = new OtpUiResponse(OtpStatus.DELIVERED, otpMessage);
		} catch (Exception e) {
			e.printStackTrace();
			otpResponseDto = new OtpUiResponse(OtpStatus.FAILED, e.getMessage());
		}
		return otpResponseDto;
	}

	public String validateOtp(OtpValidationRequest otpValidationRequest) {
		String userName=otpValidationRequest.getUsername();
		String givenOtp=otpValidationRequest.getOtpNumber();
		if (otpValidationRequest.getOtpNumber().equals(otpMap.get(userName))) {
            otpMap.remove(userName,givenOtp);
			return "OTP is valid!";
		} else {
			return "OTP is invalid!";
		}
	}

	public String generateOTP() {
		return new DecimalFormat("000000").format(new Random().nextInt(999999));
	}

	public String sendSimpleMail(EmailUiRequest request) {
		// TODO Auto-generated method stub
		

		 try {
			 
	            SimpleMailMessage mailMessage
	                = new SimpleMailMessage();
	 
	            mailMessage.setFrom(sender);
	            mailMessage.setTo(request.getRecipient());
	            mailMessage.setText(request.getMsgBody());
	            mailMessage.setSubject(request.getSubject());
	 
	            javaMailSender.send(mailMessage);
	            return "Mail Sent Successfully...";
	        }
	 
	        catch (Exception e) {
	            return "Error while Sending Mail";
	        }
		
		
	}
	
	

}
