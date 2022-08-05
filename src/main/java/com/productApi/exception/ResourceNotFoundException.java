package com.productApi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String resourceName;
	private String fileName;
	private long fieldValue;
	
	public ResourceNotFoundException(String resourceName, String fileName, long productId) {
		super(String.format("%s not found with %s:m'%S'", resourceName,fileName,productId));
		this.resourceName = resourceName;
		this.fileName = fileName;
		this.fieldValue = productId;
	}
	
	public String getResourceName() {
		return resourceName;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public long getFieldValue() {
		return fieldValue;
	}

	
}
