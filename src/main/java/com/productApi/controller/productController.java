package com.productApi.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.productApi.entity.Product;
import com.productApi.service.ProductInterface;

@RestController
@RequestMapping("/products")
public class productController {
	
	@Autowired
	private ProductInterface service;

	
	@PostMapping()
	public ResponseEntity<Product> addProduct(@RequestBody Product product){
		return new ResponseEntity<Product>(service.addProduct(product), HttpStatus.OK);
	}
	
	@GetMapping()
	public ResponseEntity<List<Product>> getProductList(){
		List<Product> productList=service.getListOfProduct();
		return ResponseEntity.ok(productList);
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<Product> getProductById(@PathVariable long productId){
		return new ResponseEntity<Product>(service.getProductById(productId), HttpStatus.OK);
	}
	
	@PutMapping()
	public ResponseEntity<Product> updateProduct(@RequestBody Product product){
		return new ResponseEntity<Product>(service.updateProduct(product),HttpStatus.OK);
	}
	
	@DeleteMapping()
	public String deleteAllProduct() {
		return service.deleteAllProduct();
	}
	
	@DeleteMapping("/{productId}")
	public String deleteProductById(@PathVariable long productId) {
		return service.deleteProductById(productId);
	}
	

	@PostMapping("/uploadProduct")
	public String uploadProductSheet(@RequestParam CommonsMultipartFile file,HttpSession session) {
		return service.uploadProductSheet(file,session);
		
	}
}












