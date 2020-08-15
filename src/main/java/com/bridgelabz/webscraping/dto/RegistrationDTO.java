package com.bridgelabz.webscraping.dto;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * Purpose : This class validate and hold details of user and transfer data to
 * service
 * 
 * @author Krunal Parate
 * @since 14-09-2020
 */
public class RegistrationDTO {
	@Valid
	@NotEmpty(message = "Please provide a firstName")
	private String firstName;
	@NotEmpty(message = "Please provide a middleName")
	private String middleName;
	@NotEmpty(message = "Please provide a lastName")
	private String lastName;
	@NotEmpty
	@Email(message = "Email should be valid")
	private String email;
	@NotEmpty(message = "Please provide a Mobile Number")
	@Pattern(regexp = "^[0-9]*$")
	private long phoneNo;
	@NotEmpty
	private String password;
	private String cofirmPasword;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(long phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCofirmPasword() {
		return cofirmPasword;
	}

	public void setCofirmPasword(String cofirmPasword) {
		this.cofirmPasword = cofirmPasword;
	}

	@Override
	public String toString() {
		return "RegistrationDTO [firstName=" + firstName + ", middleName=" + middleName + ", lastName=" + lastName
				+ ", email=" + email + ", phoneNo=" + phoneNo + ", password=" + password + ", cofirmPasword="
				+ cofirmPasword + "]";
	}
}