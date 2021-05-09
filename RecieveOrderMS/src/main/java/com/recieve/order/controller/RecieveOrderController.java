package com.recieve.order.controller;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.recieve.order.entity.OrderEntity;
import com.recieve.order.service.RecieveOrderService;

@RestController
public class RecieveOrderController {
	
	@Autowired
	RecieveOrderService service;

	private static final Logger logger = Logger.getLogger(RecieveOrderController.class);

	@RequestMapping(value = "/saveorder", method = RequestMethod.POST)
	public String saveOrder(@RequestBody OrderEntity serviceToken) {
		
		service.validateToken(serviceToken);
		
		return "isValid";
	}

}
