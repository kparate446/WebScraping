package com.bridgelabz.webscraping.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * Purpose : This class validate and hold details of user and transfer data to service
 * 
 * @author Krunal Parate
 * @since 14-09-2020
 */
public class ForgotPasswordDTO {
	@NotEmpty
	@Email(message = "Email should be valid")
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "ForgotPasswordDTO [email=" + email + "]";
	}
}