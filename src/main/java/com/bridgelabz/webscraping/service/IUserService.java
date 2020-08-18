package com.bridgelabz.webscraping.service;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.webscraping.dto.ForgotPasswordDTO;
import com.bridgelabz.webscraping.dto.LoginDTO;
import com.bridgelabz.webscraping.dto.RegistrationDTO;
import com.bridgelabz.webscraping.dto.ResetPasswordDTO;
import com.bridgelabz.webscraping.response.Response;

/**
 * Purpose : Interface for user services implemented in class
 * 
 * @author Krunal Parate
 * @since 14-08-2020
 */
public interface IUserService {
	Response addUser(RegistrationDTO registrationDTO);

	Response validateUser(String token);

	Response loginUser(LoginDTO loginDTO);

	Response forgotPassword(ForgotPasswordDTO forgotPasswordDTO);

	Response resetPassword(String token, ResetPasswordDTO resetPasswordDTO);

	Response getAllUsers();

//	Response deleteUser(String token, int id);

	Response uploadedProfilePic(String token, MultipartFile file);
}