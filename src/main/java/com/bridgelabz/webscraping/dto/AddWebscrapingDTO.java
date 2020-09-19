package com.bridgelabz.webscraping.dto;

public class AddWebscrapingDTO {
//	private LocalDateTime date = LocalDateTime.now();
	private String websiteName;
	private String email;

//	public LocalDateTime getDate() {
//		return date;
//	}
//
//	public void setDate(LocalDateTime date) {
//		this.date = date;
//	}

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

	@Override
	public String toString() {
		return "AddWebscrapingDTO [websiteName=" + websiteName + ", email=" + email + "]";
	}
}