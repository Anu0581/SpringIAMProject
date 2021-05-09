package com.recieve.order.service;

import com.recieve.order.entity.OrderEntity;

public interface RecieveOrderService {

	void validateToken(OrderEntity serviceToken);

}
