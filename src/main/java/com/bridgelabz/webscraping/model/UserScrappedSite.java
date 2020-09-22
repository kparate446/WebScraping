package com.bridgelabz.webscraping.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "userScrappedSite")
public class UserScrappedSite {
	@Id
	private String id;
	private String date;
	private String websiteName;
	private String email;
	private String userId;
	private String format;
	private String fileName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getWebsiteName() {
		return websiteName;
	}

	public void setWebsiteName(String websiteName) {
		this.websiteName = websiteName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "UserScrappedSite [id=" + id + ", date=" + date + ", websiteName=" + websiteName + ", email=" + email
				+ ", userId=" + userId + ", format=" + format + ", fileName=" + fileName + "]";
	}
}