package com.bridgelabz.webscraping.exception;

public class InvalidToken extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidToken(String message) {
		super(message);
	}
}