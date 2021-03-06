package com.bridgelabz.webscraping.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bridgelabz.webscraping.model.User;

/**
 * Purpose : Respository for performing operations in database
 * 
 * @author Krunal Parate
 * @since 14-08-2020
 */
//@Repository
public interface UserRepository extends MongoRepository<User, Object> {
	User findByEmail(String email);
}