package com.aoct.emr.communication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aoct.emr.communication.bl.CommunicationBl;
import com.aoct.emr.communication.bl.EmailUiRequest;
import com.aoct.emr.communication.response.OtpUiResponse;
import com.aoct.emr.communication.uiRequest.OtpUiRequest;
import com.aoct.emr.communication.uiRequest.OtpValidationRequest;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping("/communicate")
public class CommunicationController {

	@Autowired
	private CommunicationBl bl;

	
	@PostMapping("/sendOtp")
	public OtpUiResponse sendOtp(@RequestBody OtpUiRequest otpRequest) {
		return bl.sendSMS(otpRequest);
	}

	@PostMapping("/validateOtp")
	public String validateOtp(@RequestBody OtpValidationRequest otpValidationRequest) {
		return bl.validateOtp(otpValidationRequest);
	}

	@PostMapping("/sendMail")
	public String sendMail(@RequestBody EmailUiRequest request) {
		return bl.sendSimpleMail(request);
	}
	
	@PostMapping("/sendOtpMail/{mailId}")
	public String sendOtpMail(@PathVariable String mailId) 
	{
		return bl.sendOtpMail(mailId);
	}

	/*@PostMapping("/sendMailWithAttachment")
	public String sendMailWithAttachment(@RequestBody EmailDetails details) {
		return null;
	}*/

}
