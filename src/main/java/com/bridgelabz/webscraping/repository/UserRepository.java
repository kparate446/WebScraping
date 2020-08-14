package com.bridgelabz.webscraping.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.bridgelabz.webscraping.model.User;

/**
 * Purpose : Respository for performing operations in database
 * 
 * @author Krunal Parate
 * @since 14-08-2020
 */
@Repository
public interface UserRepository extends MongoRepository<User, Integer> {
	User findByEmail(String email);
}
