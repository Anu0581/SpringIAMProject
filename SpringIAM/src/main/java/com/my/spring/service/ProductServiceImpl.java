package com.my.spring.service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.spring.controller.GroceryLoginController;
import com.my.spring.entity.OrderEntity;
import com.my.spring.entity.ProductEntity;
import com.my.spring.repository.ProductRepo;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepo repo;

	Logger logger = LogManager.getLogger(GroceryLoginController.class);

	String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";
	Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS256.getJcaName());

	@Override
	public List<ProductEntity> getProductDetail() {
		return repo.findAll();
	}

	@Override
	public String getTransactionJwtToken(OrderEntity orderEntiry) {
		Instant now = Instant.now();
        String transactionToken = Jwts.builder()
        		.claim("productName",orderEntiry.getProductName())
        		.claim("quantity", orderEntiry.getQuantity())
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(5l, ChronoUnit.MINUTES)))
                .signWith(hmacKey)
                .compact();
		return transactionToken;
	}

	@Override
	public String getServiceToken(String trasactionToken, String userToken) {
		Instant now = Instant.now();
        String serviceToken = Jwts.builder()
        		.claim("transactionToken",trasactionToken)
        		.claim("userToken", userToken)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(5l, ChronoUnit.MINUTES)))
                .signWith(hmacKey)
                .compact();
		return serviceToken;
	}

	@Override
	public void updateProductQuantity(OrderEntity orderEntiry) {
		ProductEntity productResponse = repo.findByProduct(orderEntiry.getProductName());
		if(!Objects.isNull(productResponse)) {
			if(productResponse.getQuantity()>orderEntiry.getQuantity()) {
				productResponse.setQuantity(productResponse.getQuantity()-orderEntiry.getQuantity());
				repo.save(productResponse);
			}else {
				repo.delete(productResponse);
			}
		}
		
	}

}
