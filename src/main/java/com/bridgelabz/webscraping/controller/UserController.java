package com.bridgelabz.webscraping.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.webscraping.dto.ForgotPasswordDTO;
import com.bridgelabz.webscraping.dto.LoginDTO;
import com.bridgelabz.webscraping.dto.RegistrationDTO;
import com.bridgelabz.webscraping.dto.ResetPasswordDTO;
import com.bridgelabz.webscraping.response.Response;
import com.bridgelabz.webscraping.service.IUserService;

/**
 * Purpose : API Define
 * 
 * @author Krunal Parate
 * @since 14-08-2020
 */
@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private IUserService service;

	/**
	 * Purpose : User registration
	 * 
	 * @param registrationDTO
	 * @return : Response
	 */
	@PostMapping("/register")
	public ResponseEntity<String> adduser(@Valid @RequestBody RegistrationDTO registrationDTO) {
		Response response = service.addUser(registrationDTO);
		return new ResponseEntity<String>(response.getMessage(), HttpStatus.OK);
	}

	/**
	 * Purpose : Verification of user
	 * 
	 * @param token : Verified the token
	 * @return :- Response
	 */
	@PostMapping("/validation")
	public ResponseEntity<String> verifiedUser(@RequestHeader String token) {
		Response response = service.verifiedUser(token);
		return new ResponseEntity<String>(response.getMessage(), HttpStatus.OK);
	}

	/**
	 * Purpose : User login
	 * 
	 * @param loginDTO
	 * @return : Response
	 */
	@PostMapping("/login")
	public ResponseEntity<Response> login(@Valid @RequestBody LoginDTO loginDTO) {
		Response response = service.loginUser(loginDTO);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	/**
	 * Purpose : Forgot password
	 * 
	 * @param forgotPasswordDTO
	 * @return :response
	 */
	@PostMapping("/forgotpassword")
	public ResponseEntity<String> forgotPassword(@Valid @RequestBody ForgotPasswordDTO forgotPasswordDTO) {
		Response response = service.forgotPassword(forgotPasswordDTO);
		return new ResponseEntity<String>(response.getMessage(), HttpStatus.OK);
	}

	/**
	 * Purpose :- Reset Password
	 * 
	 * @param token            : Verified the Token
	 * @param resetPasswordDTO : Access the resetPasswordDTO Data
	 * @return : Response
	 */
	@PutMapping("/resetpassword")
	public ResponseEntity<String> resetpassword(@RequestHeader String token, ResetPasswordDTO resetPasswordDTO) {
		Response response = service.resetPassword(token, resetPasswordDTO);
		return new ResponseEntity<String>(response.getMessage(), HttpStatus.OK);
	}
}