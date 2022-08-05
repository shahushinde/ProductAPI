package com.productApi.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.productApi.entity.Product;

public interface ProductInterface {
	
	Product addProduct(Product product);
	
	List<Product> getListOfProduct();
	
	Product getProductById(long productId);
	
	Product updateProduct(Product product);
	
	String deleteAllProduct();
	
	String deleteProductById(long productId);
	
	public String uploadProductSheet(CommonsMultipartFile file,HttpSession session);
	
	

}
