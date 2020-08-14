package com.bridgelabz.webscraping.service;

import com.bridgelabz.webscraping.dto.LoginDTO;
import com.bridgelabz.webscraping.dto.RegistrationDTO;
import com.bridgelabz.webscraping.response.Response;

/**
 * Purpose : Interface for user services implemented in class
 * 
 * @author Krunal Parate
 * @since 14-08-2020
 */
public interface IUserService {
	Response addUser(RegistrationDTO registrationDTO);
	Response loginUser(LoginDTO loginDTO);
}