package com.bridgelabz.webscraping.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.webscraping.configuration.PasswordConfiguration;
import com.bridgelabz.webscraping.dto.LoginDTO;
import com.bridgelabz.webscraping.dto.RegistrationDTO;
import com.bridgelabz.webscraping.model.User;
import com.bridgelabz.webscraping.repository.UserRepository;
import com.bridgelabz.webscraping.response.Response;
import com.bridgelabz.webscraping.utility.JwtToken;
import com.sun.istack.logging.Logger;

/**
 * Purpose :- This class implements all methods of interface. which performs
 *          CRUD of user in database
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
		// decodes the password
		if (passConfig.encoder().matches(loginDTO.getPassword(), user.getPassword())) {
			LOGGER.info("Login successfully");
			System.out.println(token);
			return new Response(200, "Login successfully", token);
		} else {
			LOGGER.warning("Invalid password");
			return new Response(400, "Invalid password", false);
		}
	}
}