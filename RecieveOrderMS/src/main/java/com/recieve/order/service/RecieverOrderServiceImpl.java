package com.recieve.order.service;

import java.security.Key;
import java.util.Base64;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recieve.order.entity.OrderEntity;
import com.recieve.order.entity.TokenEntity;
import com.recieve.order.repo.RecieveOrderRepo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class RecieverOrderServiceImpl implements RecieveOrderService{
	
	@Autowired
	RecieveOrderRepo repo;

	@Override
	public void validateToken(OrderEntity serviceToken) {
		String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";
		Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS256.getJcaName());
		
		Claims serviceTkn = Jwts.parserBuilder().setSigningKey(hmacKey).build()
				.parseClaimsJws(serviceToken.getServiceToken()).getBody();
		String transactionTkn = serviceTkn.get("transactionToken",String.class);
		String userToken = serviceTkn.get("userToken",String.class);
		
		Claims transactionToken = Jwts.parserBuilder().setSigningKey(hmacKey).build()
				.parseClaimsJws(transactionTkn).getBody();
		
		saveTokeninDB(serviceToken.getServiceToken(),transactionTkn,userToken);
		
	}

	private void saveTokeninDB(String serviceToken, String transactionTkn, String userToken) {
		TokenEntity entity = new TokenEntity();
		entity.setServiceToken(serviceToken);
		entity.setTransactionToken(transactionTkn);
		entity.setUserToken(userToken);
		repo.save(entity);
		
	}

}
