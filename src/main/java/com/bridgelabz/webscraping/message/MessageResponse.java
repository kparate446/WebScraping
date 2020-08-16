package com.bridgelabz.webscraping.message;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class MessageResponse {
	public SimpleMailMessage verifyMail(String reciverEmail, String reciverName, String token) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(reciverEmail);
		message.setFrom("forgotbridge70@gmail.com");
		message.setSubject("Complete Verification!!!! ");
		message.setText("token" + token);
		return message;
	}
}