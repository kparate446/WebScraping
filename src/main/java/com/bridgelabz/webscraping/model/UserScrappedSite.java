package com.bridgelabz.webscraping.model;

import java.time.LocalDateTime;
import java.util.StringJoiner;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "userScrappedSite")
public class UserScrappedSite {
	@Id
	private String id;
	private LocalDateTime date = LocalDateTime.now();
	private StringJoiner websiteName;
	private String email;
	private String userId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public StringJoiner getWebsiteName() {
		return websiteName;
	}

	public void setWebsiteName(StringJoiner joiner) {
		this.websiteName = joiner;
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

	@Override
	public String toString() {
		return "UserScrappedSite [id=" + id + ", date=" + date + ", websiteName=" + websiteName + ", email=" + email
				+ ", userId=" + userId + "]";
	}
}