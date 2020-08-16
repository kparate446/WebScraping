package com.bridgelabz.webscraping.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Purpose : This class validate and hold details of user and transfer data to
 * service
 * 
 * @author Krunal Parate
 * @since 14-09-2020
 */
public class ResetPasswordDTO {
	@NotBlank(message = "Password is mandatory")
	@Size(min = 2, max = 30)
	private String password;

	@NotBlank(message = "Confirm password is mandatory")
	@Size(min = 2, max = 30)
	private String confirmPassword;

	public ResetPasswordDTO(String password, String confirmPassword) {
		this.password = password;
		this.confirmPassword = confirmPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	public String toString() {
		return "ResetPasswordDTO [password=" + password + ", confirmPassword=" + confirmPassword + "]";
	}
}