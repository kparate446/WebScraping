package com.bridgelabz.webscraping.service;

import com.bridgelabz.webscraping.response.Response;

public interface IUserScrappedSiteService {
	Response addScrappedSite(String url, String format, String token) throws Exception;

	Response getWebScrapingData(String token, String filePath) throws Exception;

	Response getAllWebSrapingSite(String format, String token);
}