package com.bridgelabz.webscraping.message;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class MessageResponse {
	public SimpleMailMessage verifyMail(String reciverEmail, String token, String link) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(reciverEmail);
		message.setFrom("kparate03@gmail.com");
		message.setSubject("Complete Verification!!!! ");
		message.setText("Hello "+(link+token));//for sending the token to email
		return message;
	}
}