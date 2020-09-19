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
import com.bridgelabz.webscraping.exception.EmptyFile;
import com.bridgelabz.webscraping.exception.FileNotUploaded;
import com.bridgelabz.webscraping.exception.InvalidPassword;
import com.bridgelabz.webscraping.exception.InvalidUser;
import com.bridgelabz.webscraping.exception.UserAlreadyPresent;
import com.bridgelabz.webscraping.message.MessageData;
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
	private MessageData messageData;

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
			throw new UserAlreadyPresent(messageData.userAlready_Present);
		}
		// store new user data in mapper
		User userData = mapper.map(registrationDTO, User.class);
		// To generate the Token
		String token = jwtToken.generateToken(userData.getEmail());
		// Send the token in email
		email = massageResponse.verifyMail(userData.getEmail(), token,
				"\t Validate your email \n" + "http://localhost:8080/validate?token=");
		emailSender.sendEmail(email);
		if (registrationDTO.getPassword().equals(registrationDTO.getCofirmPasword())) {
			// Encoded the password stored in database
			userData.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
			userRepository.save(userData);
			// Print token in console
			System.out.println(token);
			LOGGER.info("New user is successfully created");
			return new Response(200, "New user is successfully created", token);
		} else {
			LOGGER.warning("Invalid password");
			throw new InvalidPassword(messageData.Invalid_Password);
		}
	}

	/**
	 * login user through email id or password
	 */
	@Override
	public Response loginUser(LoginDTO loginDTO) {
		User user = userRepository.findByEmail(loginDTO.getEmail());
		// generate the token
		String token = jwtToken.generateToken(loginDTO.getEmail());
		// Check if user is present or not
		if (user == null) {
			LOGGER.warning("Invalid User");
			throw new InvalidUser(messageData.Invalid_User);
		}
		if (user.isValidate()) {
			// decode the password
			if (passConfig.encoder().matches(loginDTO.getPassword(), user.getPassword())) {
				email = massageResponse.verifyMail(loginDTO.getEmail(), token, "\t Logged-In user \n" + "token=");
				emailSender.sendEmail(email);
				LOGGER.info("You are Successfully Logged-in");
				System.out.println(token);
				return new Response(200, "You are Successfully Logged-in", token);
			} else {
				LOGGER.warning("Invalid password");
				throw new InvalidPassword(messageData.Invalid_Password);
			}
		} else {
			LOGGER.warning("Not Valid");
			throw new InvalidUser(messageData.Invalid_User);
		}
	}

	/**
	 * token user send verify token for checking for token is match or not
	 */
	@Override
	public Response validateUser(String token) {
		String email = jwtToken.getToken(token);
		User user = userRepository.findByEmail(email);
		// Check if user is present or not
		if (user == null) {
			LOGGER.warning("Invalid User");
			throw new InvalidUser(messageData.Invalid_User);
		} else
			user.setValidate(true);
		userRepository.save(user);
		LOGGER.info("Email is Verified Successfully");
		return new Response(200, "Email is Verified Successfully", token);
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
			throw new InvalidUser(messageData.Invalid_User);
		}
		if (user.isValidate()) {
			String token = jwtToken.generateToken(forgotPasswordDTO.getEmail());
			email = massageResponse.verifyMail(forgotPasswordDTO.getEmail(), token,
					"\t Forgot password \n" + "http://localhost:4200/resetpassword");
			emailSender.sendEmail(email);
			userRepository.save(user);
			System.out.println(token);
			LOGGER.info("Sent the token in mail");
			return new Response(200, "Sent the token in mail", token);
		}
		LOGGER.warning("Invalid User");
		throw new InvalidUser(messageData.Invalid_User);
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
			throw new InvalidUser(messageData.Invalid_User);
		} else if (resetPasswordDTO.getPassword().equals(resetPasswordDTO.getConfirmPassword())) {
			user.setPassword(passwordEncoder.encode(resetPasswordDTO.getPassword()));
			userRepository.save(user);
			LOGGER.info("Password reset successfully");
			return new Response(200, "Password Reset Successfully", true);
		}
		LOGGER.warning("Invalid Password");
		throw new InvalidPassword(messageData.Invalid_Password);
	}

	/**
	 * show all user details
	 */
	@Override
	public Response getAllUsers() {
		if (userRepository.findAll() == null) {
			LOGGER.warning("Invalid user");
			throw new InvalidUser(messageData.Invalid_User);
		} else {
			List<User> users = userRepository.findAll();
			LOGGER.info("Successfully showing the User list data");
			return new Response(200, "Show all users", users);
		}
	}

	/**
	 * delete particular user in database though user id
	 */
	@Override
	public Response deleteUser(String token, String id) {
		String email = jwtToken.getToken(token);
		User user = userRepository.findByEmail(email);
		// Check if user is present or not
		if (user == null) {
			LOGGER.warning("Invalid user");
			throw new InvalidUser(messageData.Invalid_User);
		}
		if (id == user.getId()) {
			userRepository.deleteById(id);
			LOGGER.info("Successfully deleted user");
			return new Response(200, "Successfully deleted user", true);
		} else {
			throw new InvalidUser(messageData.Invalid_User);
		}
	}

	/**
	 * Uploading Image to User
	 */
	@Override
	public Response uploadedProfilePic(String token, MultipartFile file) {
		String email = jwtToken.getToken(token);
		User user = userRepository.findByEmail(email);
		// Check if user is present or not
		if (user == null) {
			LOGGER.warning("Invalid user");
			throw new InvalidUser(messageData.Invalid_User);
		}
		// file is empty or not
		if (file.isEmpty())
			throw new EmptyFile(messageData.File_Is_Empty);
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
			// this upload the image on cloudinary
			uploadProfile = cloudinary.uploader().upload(uploadFile, ObjectUtils.emptyMap());
		} catch (IOException e) {
			throw new FileNotUploaded(messageData.File_Not_Upload);
		}
		// Set profile picture in url type in database
		user.setProfilePic(uploadProfile.get("secure_url").toString());
		userRepository.save(user);
		LOGGER.info("Successfully uploaded the profile picture");
		return new Response(200, "Successfully Uploaded Profile picture", true);
	}
}