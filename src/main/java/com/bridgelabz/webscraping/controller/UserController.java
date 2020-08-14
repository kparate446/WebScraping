package com.bridgelabz.webscraping.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.webscraping.dto.LoginDTO;
import com.bridgelabz.webscraping.dto.RegistrationDTO;
import com.bridgelabz.webscraping.response.Response;
import com.bridgelabz.webscraping.service.IUserService;

/**
 *  Purpose : API Define
 *  
 * @author  Krunal Parate
 * @since 14-08-2020
 */
@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private IUserService service;

	/**
	 * Purpose :- User registration
	 * @param registrationDTO
	 * @return :- Response
	 */
	@PostMapping("/register")
	public ResponseEntity<String> adduser(@Valid @RequestBody RegistrationDTO registrationDTO) {
		Response response = service.addUser(registrationDTO);
		return new ResponseEntity<String>(response.getMessage(), HttpStatus.OK);
	}

	/**
	 * Purpose :- User login
	 * @param loginDTO
	 * @return :- Response
	 */
	@PostMapping("/login")
	public ResponseEntity<Response> login(@Valid @RequestBody LoginDTO loginDTO) {
		Response response = service.loginUser(loginDTO);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
}