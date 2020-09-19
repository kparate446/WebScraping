package com.bridgelabz.webscraping.service;

import com.bridgelabz.webscraping.response.Response;

public interface IUserScrappedSiteService {
	public Response addScrappedSite(String url, String format, String token) throws Exception;

	public Response getWebScrapingData(String token, String filePath) throws Exception;
}