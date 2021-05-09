package com.my.spring.service;

import java.util.List;

import com.my.spring.entity.OrderEntity;
import com.my.spring.entity.ProductEntity;

public interface ProductService {

	List<ProductEntity> getProductDetail();

	String getTransactionJwtToken(OrderEntity orderEntiry);

	String getServiceToken(String trasactionToken, String userToken);

	void updateProductQuantity(OrderEntity orderEntiry);


}
