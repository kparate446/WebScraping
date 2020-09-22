package com.bridgelabz.webscraping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.webscraping.response.Response;
import com.bridgelabz.webscraping.service.IUserScrappedSiteService;

/**
 * Purpose : API Define
 * 
 * @author Krunal Parate
 * @since 19-08-2020
 */
@CrossOrigin(origins = "http://localhost:4200")
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
	@PostMapping("/addwebscripe")
	public ResponseEntity<String> addWebScraping(@RequestParam String url,@RequestParam String format, @RequestHeader String token) throws Exception {
		Response response = service.addScrappedSite(url,format, token);
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
	@PostMapping("/getwebscripe")
	public ResponseEntity<Response> getWebScripeData(@RequestParam String filePath, @RequestHeader String token) throws Exception {
		Response response = service.getWebScrapingData(filePath,token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}	
	
	/**
	 * Purpose : Already retrieving data that websites have been scrapped
	 * @param format : Which format do you want the data from
	 * @param token : Verified the user
	 * @return : Response
	 */
	@GetMapping("/getalluserscrapedsite")
	public ResponseEntity<Response> getAllUserScrapedSite(@RequestParam String format, @RequestHeader String token) {
		Response response = service.getAllWebSrapingSite(format, token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}	
}