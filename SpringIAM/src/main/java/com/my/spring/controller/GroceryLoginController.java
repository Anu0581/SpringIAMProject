package com.my.spring.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.my.spring.entity.OrderEntity;
import com.my.spring.entity.ProductEntity;
import com.my.spring.service.ProductService;

@RestController
public class GroceryLoginController {

	@Autowired
	OAuth2AuthorizedClientService authclientService;

	@Autowired
	ProductService service;
	
	@Autowired
	RestTemplate restTemplate;
	
	OAuth2AuthorizedClient client;

	Logger logger = LogManager.getLogger(GroceryLoginController.class);
	
	String url="http://localhost:8082/saveorder";

	@RequestMapping("/userInfo")
	public void userInfo(Principal principal) {
		final OAuth2AuthenticationToken oAuth2Authentication = (OAuth2AuthenticationToken) principal;
		logger.info(oAuth2Authentication);
		client = authclientService.loadAuthorizedClient(oAuth2Authentication.getAuthorizedClientRegistrationId(),
				oAuth2Authentication.getName());
		logger.info("Token Value : " + client.getAccessToken().getTokenValue());
		logger.info("Token Value : " + oAuth2Authentication.getDetails().toString());

	}

	@RequestMapping("/api/user")
	public Map<String, Object> user(@AuthenticationPrincipal DefaultOidcUser oidcUser) {
		return oidcUser.getClaims();
	}

	@RequestMapping("/api/product")
	public List<ProductEntity> getProductDetail() {
		return service.getProductDetail();
	}

	@RequestMapping(value = "/postOrder", method = RequestMethod.POST)
	public String postOrder(OrderEntity orderEntiry) throws Exception {
		if(client.getAccessToken().getTokenValue()==null) {
			throw new Exception("User Token Expired, please Logout and login Again");
		}
		logger.info("recieved...........", client.getAccessToken().getTokenValue());
		String trasactionToken = service.getTransactionJwtToken(orderEntiry);
		logger.info("recieved...........", trasactionToken);
		String serviceToken = service.getServiceToken(trasactionToken, client.getAccessToken().getTokenValue());
		logger.info("recieved...........", serviceToken);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		orderEntiry.setServiceToken(serviceToken);
		HttpEntity<OrderEntity> entity = new HttpEntity<OrderEntity>(orderEntiry);

		String response = restTemplate.postForObject( url, entity , String.class);
		if(response.equalsIgnoreCase("isValid")){
			service.updateProductQuantity(orderEntiry);
		}

		return "Updated Successfully";
	}

}
