package com.bridgelabz.webscraping.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Purpose : User POJO class & userDetails table created
 *
 * @author Krunal Parate
 * @since 14-09-2020
 */
@Document(collection = "userDetails")
public class User {
	@Id
	private String id;
	private String firstName;
	private String middleName;
	private String lastName;
	private String email;
	private String password;
	private Date date = new Date();
	private long phoneNo;
	private String profilePic;
	private boolean isValidate = false;

	// this list use for relationship between User and UserScrappedSite
	@DBRef
	private List<UserScrappedSite> scrap = new ArrayList<UserScrappedSite>();

	public List<UserScrappedSite> getScrap() {
		return scrap;
	}

	public void setScrap(List<UserScrappedSite> scrap) {
		this.scrap = scrap;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(long phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public boolean isValidate() {
		return isValidate;
	}

	public void setValidate(boolean isValidate) {
		this.isValidate = isValidate;
	}

	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", middleName=" + middleName + ", lastName=" + lastName + ", email="
				+ email + ", password=" + password + ", date=" + date + ", phoneNo=" + phoneNo + ", profilePic="
				+ profilePic + ", isValidate=" + isValidate + "]";
	}
}