package com.bridgelabz.webscraping.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
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
		// store new user data in mapper
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
	 * login user though email id or password
	 */
	@Override
	public Response loginUser(LoginDTO loginDTO) {
		User user = userRepository.findByEmail(loginDTO.getEmail());
		// generate the token
		String token = jwtToken.generateToken(loginDTO.getEmail());
		// Check if user is present or not
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
	 * token user send verify token for checking for token is match or not
	 */
	@Override
	public Response verifiedUser(String token) {
		String email = jwtToken.getToken(token);
		User user = userRepository.findByEmail(email);
		// Check if user is present or not
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
	 * find user email present or not and validate for checking send email for
	 * verify user email id
	 */
	@Override
	public Response forgotPassword(ForgotPasswordDTO forgotPasswordDTO) {
		User user = userRepository.findByEmail(forgotPasswordDTO.getEmail());
		// Check if user is present or not
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
	 * Purpose user send new password for changing password should change i
	 */
	@Override
	public Response resetPassword(String token, ResetPasswordDTO resetPasswordDTO) {
		String email = jwtToken.getToken(token);
		User user = userRepository.findByEmail(email);
		// Check if user is present or not
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

	/**
	 * show all user details
	 */
	@Override
	public Response getAllUsers() {
		if (userRepository.findAll() == null) {
			LOGGER.warning("Invalid user");
			return new Response(400, "Invalid user", false);
		} else {
			List<User> users = userRepository.findAll();
			LOGGER.info("Successfully showing the User table data");
			return new Response(200, "Show all users", users);

		}
	}

	/**
	 * delete particular user in database though user id
	 */
	@Override
	public Response deleteUser(String token, int id) {
		String email = jwtToken.getToken(token);
		User user = userRepository.findByEmail(email);
		// Check if user is present or not
		if (user == null) {
			LOGGER.warning("Invalid user");
			return new Response(400, "Invalid user", false);
		}
		if (id == user.getId()) {
			userRepository.deleteById(id);
			LOGGER.info("Successfully deleted user");
			return new Response(200, "Successfully deleted user", true);
		} else {
			return new Response(400, "Invalid User", false);
		}
	}

	/**
	 * Uploading Image to User
	 */
	@Override
	public Response uploadImage(String token, MultipartFile file) {
		String email = jwtToken.getToken(token);
		User user = userRepository.findByEmail(email);
		// Check if user is present or not
		if (user == null) {
			LOGGER.warning("Invalid user");
			return new Response(400, "Invalid user", false);
		}
		// file is empty or not
		if (file.isEmpty())
			return new Response(400, "File is Empty", false);
		File uploadFile = new File(file.getOriginalFilename());
		try {
			BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(uploadFile));
			try {
				outputStream.write(file.getBytes());
				outputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// Connection of close cloudinary properties
		Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name", "dmlqjysiv", "api_key",
				"242443158528625", "api_secret", "q9p9sxtwVI-kSM5CVt-Yrc4_B0c"));
		Map<?, ?> uploadProfile;
		try {
			// this upload the image on cloudinary ->Query -> Mapped
			uploadProfile = cloudinary.uploader().upload(uploadFile, ObjectUtils.emptyMap());
		} catch (IOException e) {
			return new Response(400, "File not uploaded", false);
		}
		// Set profile picture in url type in database
		user.setProfilePic(uploadProfile.get("secure_url").toString());
		userRepository.save(user);
		LOGGER.info("Successfully uploaded the profile picture");
		return new Response(200, "Uploaded Profile picture Successfully", true);
	}
}