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

	Response verifiedUser(String token);

	Response loginUser(LoginDTO loginDTO);

	Response forgotPassword(ForgotPasswordDTO forgotPasswordDTO);

	Response resetPassword(String token, ResetPasswordDTO resetPasswordDTO);

	Response getAllUsers();

	Response deleteUser(String token, String id);

	Response uploadImage(String token, MultipartFile file);
}