package com.bridgelabz.webscraping.exception;

public class EmptyFile extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public EmptyFile(String message) {
		super(message);
	}
}
