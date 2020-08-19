package com.bridgelabz.webscraping.service;

import java.util.StringJoiner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.webscraping.message.MessageData;
import com.bridgelabz.webscraping.repository.UserRepository;
import com.bridgelabz.webscraping.response.Response;
import com.bridgelabz.webscraping.utility.JwtToken;
import com.sun.istack.logging.Logger;

/**
 * Purpose :- This class implements all methods of interface. which performs
 * CRUD of user in database
 * 
 * @author Krunal Parate
 * @since 14-08-2020
 */

@Service
public class UserScrappedSiteServiceImp implements IUserScrappedSiteService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	Response response;

	@Autowired
	private MessageData messageData;

	@Autowired
	private JwtToken jwtToken;

	private static final Logger LOGGER = Logger.getLogger(UserServiceImp.class);

	@Override
	public Response addScrappedSite(String url) throws Exception {
		Document doc = Jsoup.connect(url).get();

		for (Element table : doc.getElementsByTag("table")) {
			for (Element trElement : table.getElementsByTag("tr")) {
				StringJoiner joiner = new StringJoiner(",");
				for (Element tdElement : trElement.getElementsByTag("th")) {
					joiner.add(tdElement.text());
				}
				for (Element element : trElement.getElementsByTag("td")) {
					joiner.add(element.text());
				}
				System.out.println(joiner);
			}
		}
		return new Response(200, "Successfully Scrapped data", true);
	}
}