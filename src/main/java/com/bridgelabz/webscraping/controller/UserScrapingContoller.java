package com.bridgelabz.webscraping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.webscraping.response.Response;
import com.bridgelabz.webscraping.service.IUserScrappedSiteService;

/**
 * Purpose : API Define
 * 
 * @author Krunal Parate
 * @since 19-08-2020
 */
@CrossOrigin(origins ="*", maxAge = 3600)
@RestController
@RequestMapping("/webScrape")
public class UserScrapingContoller {
	@Autowired
	private IUserScrappedSiteService service;

	/**
	 * Purpose : Scraping websites using Jsoup and inserting data into databases
	 * using PdfBox (e.g .pdf, .html and .csv format)
	 * 
	 * @param token : Verified the user
	 * @param url : Which website to scrapped
	 * @return : Response
	 * @throws Exception
	 */
	@PostMapping("/addwebscripe/{token}/{url}")
	public ResponseEntity<String> addWebScraping(String token, String url) throws Exception {
		Response response = service.addScrappedSite(token, url);
		return new ResponseEntity<String>(response.getMessage(), HttpStatus.OK);
	}
	
	/**
	 * Purpose : Getting all Web Scraping data details
	 * 
	 * @param token : Verified the user
	 * @param fileName
	 * @return : Response
	 * @throws Exception
	 */
	@GetMapping("/getwebscripe/{token}/{filePath}")
	public ResponseEntity<Response> getWebScripeData(String token, String filePath) throws Exception {
		Response response = service.getWebScrapingData(token, filePath);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}	
}