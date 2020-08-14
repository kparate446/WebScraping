package com.bridgelabz.webscraping.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Purpose : This class validate and hold details of user and transfer data to service
 * 
 * @author Krunal Parate
 * @since  14-09-2020
 */
public class LoginDTO {
	@NotEmpty
	@Email(message = "Email should be valid")
	private String email;
	@NotEmpty
	@Size(min = 2, max = 30)
	private String password;

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "LoginDTO [email=" + email + ", password=" + password + "]";
	}
}