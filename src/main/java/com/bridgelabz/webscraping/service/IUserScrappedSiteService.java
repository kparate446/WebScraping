package com.bridgelabz.webscraping.service;

import com.bridgelabz.webscraping.response.Response;

public interface IUserScrappedSiteService {
	Response addScrappedSite(String url) throws Exception;
}