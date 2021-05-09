package com.my.spring;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.my.spring.entity.ProductEntity;
import com.my.spring.repository.ProductRepo;

@SpringBootApplication
public class SpringIamApplication {

	@Autowired
	ProductRepo repo;

	public static void main(String[] args) {
		SpringApplication.run(SpringIamApplication.class, args);
	}

	@PostConstruct
	public void insertData() {
		repo.save(new ProductEntity("Apple", 80, 10));
		repo.save(new ProductEntity("Onion", 100, 10));
		repo.save(new ProductEntity("Tomato", 20, 10));
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
