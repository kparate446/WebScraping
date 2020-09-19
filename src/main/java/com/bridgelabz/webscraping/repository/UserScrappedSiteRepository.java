package com.bridgelabz.webscraping.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.webscraping.model.UserScrappedSite;

/**
 * Purpose : Respository for performing operations in database
 * 
 * @author Krunal Parate
 * @since 14-08-2020
 */
@Repository
public interface UserScrappedSiteRepository extends MongoRepository<UserScrappedSite, Object> {
	UserScrappedSite findByEmail(String email);
}
