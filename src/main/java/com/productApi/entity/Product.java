package com.productApi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="product")
public class Product {
	
	@Id
	@Column(name="productid")
	private long productId;
	
	@Column(name="productname", nullable = false)
	private String productName;
	
	
	@Column(name="productcost" )
	private double productCost;
	
	@Column(name="productexpirydate" )
	private String productExpiryDate;
	


}
