package com.bridgelabz.webscraping.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.webscraping.configuration.PasswordConfiguration;
import com.bridgelabz.webscraping.dto.ForgotPasswordDTO;
import com.bridgelabz.webscraping.dto.LoginDTO;
import com.bridgelabz.webscraping.dto.RegistrationDTO;
import com.bridgelabz.webscraping.dto.ResetPasswordDTO;
import com.bridgelabz.webscraping.message.MessageResponse;
import com.bridgelabz.webscraping.model.User;
import com.bridgelabz.webscraping.repository.UserRepository;
import com.bridgelabz.webscraping.response.Response;
import com.bridgelabz.webscraping.utility.EmailSenderService;
import com.bridgelabz.webscraping.utility.JwtToken;
import com.sun.istack.logging.Logger;

/**
 * Purpose :- This class implements all methods of interface. which performs
 * CRUD of user in database
 * 
 * @author Krunal Parate
 * @since 14-08-2020
 */
@Service
public class UserServiceImp implements IUserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	Response response;

	@Autowired
	private EmailSenderService emailSender;

	@Autowired
	private MessageResponse massageResponse;

	SimpleMailMessage email;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	PasswordConfiguration passConfig;

	@Autowired
	private JwtToken jwtToken;

	private static final Logger LOGGER = Logger.getLogger(UserServiceImp.class);

	/**
	 * This method is used for register new User in database
	 */
	@Override
	public Response addUser(RegistrationDTO registrationDTO) {
		User user = userRepository.findByEmail(registrationDTO.getEmail());
		// Check if user is already present or not
		if (user != null) {
			LOGGER.warning("User is already present");
			return new Response(400, "User is already present", false);
		}
		User userData = mapper.map(registrationDTO, User.class);
		// To generate the Token
		String token = jwtToken.generateToken(userData.getEmail());
		// Send the token in email
		email = massageResponse.verifyMail(userData.getEmail(), userData.getFirstName(), token);
		emailSender.sendEmail(email);
		if (registrationDTO.getPassword().equals(registrationDTO.getCofirmPasword())) {
			// Encoded the password stored in database
			userData.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
			userRepository.save(userData);
			// Print token in console
			System.out.println(token);
			LOGGER.info("Registration puccessfully");
			return new Response(200, "Registration successfully", token);
		} else {
			LOGGER.warning("Invalid password");
			return new Response(400, "Invalid password", false);
		}
	}

	/**
	 * Login user
	 */
	@Override
	public Response loginUser(LoginDTO loginDTO) {
		User user = userRepository.findByEmail(loginDTO.getEmail());
		// generate the token
		String token = jwtToken.generateToken(loginDTO.getEmail());
		if (user == null) {
			LOGGER.warning("Invalid User");
			return new Response(400, "Invalid user", false);
		}
		if (user.isValidate()) {
			// decode the password
			if (passConfig.encoder().matches(loginDTO.getPassword(), user.getPassword())) {
				email = massageResponse.verifyMail(user.getEmail(), user.getFirstName(), token);
				emailSender.sendEmail(email);
				LOGGER.info("Login successfully");
				System.out.println(token);
				return new Response(200, "Login successfully", token);
			} else {
				LOGGER.warning("Invalid password");
				return new Response(400, "Invalid password", false);
			}
		} else {
			LOGGER.warning("Not Valid");
			return new Response(400, "Not Valid", false);
		}
	}

	/**
	 * Verified user
	 */
	@Override
	public Response verifiedUser(String token) {
		String email = jwtToken.getToken(token);
		User user = userRepository.findByEmail(email);
		if (user == null) {
			LOGGER.warning("Invalid User");
			return new Response(400, "Invalid user", false);
		} else
			user.setValidate(true);
		userRepository.save(user);
		LOGGER.info("Successfully verified user");
		return new Response(200, "Successfully verified user", token);
	}

	/**
	 * Forgot password
	 */
	@Override
	public Response forgotPassword(ForgotPasswordDTO forgotPasswordDTO) {
		User user = userRepository.findByEmail(forgotPasswordDTO.getEmail());
		if (user == null) {
			LOGGER.warning("Invalid user");
			return new Response(400, "Invalid user", false);
		}
		if (user.isValidate()) {
			String token = jwtToken.generateToken(forgotPasswordDTO.getEmail());
			email = massageResponse.verifyMail(user.getEmail(), user.getFirstName(), token);
			emailSender.sendEmail(email);
			userRepository.save(user);
			System.out.println(token);
			LOGGER.info("Sent the token in mail");
			return new Response(200, "Sent the token in mail", token);
		}
		LOGGER.warning("Invalid User");
		return new Response(400, "Invalid user", false);
	}

	/**
	 * Reset Password
	 */
	@Override
	public Response resetPassword(String token, ResetPasswordDTO resetPasswordDTO) {
		String email = jwtToken.getToken(token);
		User user = userRepository.findByEmail(email);
		if (user == null) {
			LOGGER.warning("Invalid user");
			return new Response(400, "Invalid user", false);
		} else if (resetPasswordDTO.getPassword().equals(resetPasswordDTO.getConfirmPassword())) {
			user.setPassword(passwordEncoder.encode(resetPasswordDTO.getPassword()));
			userRepository.save(user);
			LOGGER.info("Password reset successfully");
			return new Response(200, "Password Reset Successfully", true);
		}
		LOGGER.warning("Invalid Password");
		return new Response(400, "Invalid password", false);
	}
}