package com.bridgelabz.webscraping.service;

import com.bridgelabz.webscraping.response.Response;

public interface IUserScrappedSiteService {
	Response addScrappedSite(String token, String url) throws Exception;

	Response getWebScrapingData(String token, String fileName) throws Exception;
}