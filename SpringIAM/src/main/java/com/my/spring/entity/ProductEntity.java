package com.my.spring.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {

	@Id
	@GeneratedValue
	private Long sNo;

	private String product;

	private int productPrice;

	private int quantity;

	public ProductEntity(String product, int productPrice, int quantity) {
		super();
		this.product = product;
		this.productPrice = productPrice;
		this.quantity = quantity;
	}


}
