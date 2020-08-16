package com.bridgelabz.webscraping.exception;

public class UserAlreadyPresent extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UserAlreadyPresent(String message) {
		super(message);
	}
}
