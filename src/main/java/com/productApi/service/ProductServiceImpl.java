package com.productApi.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.aspectj.weaver.NewMethodTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.productApi.dao.ProductDao;
import com.productApi.entity.Product;
import com.productApi.exception.ResourceNotFoundException;

@Service
public class ProductServiceImpl implements ProductInterface {
	
	@Autowired
	private ProductDao dao;
	
	@Override
	public Product addProduct(Product product) {
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new java.util.Date());
		long id=Long.parseLong(timeStamp);
		product.setProductId(id);
	dao.save(product);
		return product;
	}

	@Override
	public List<Product> getListOfProduct() {
		List<Product> list=dao.findAll();
		return list;
	}

	@Override
	public Product getProductById(long productId) {
//		Optional<Product> product=dao.findById(productId);
//		if(product.isPresent()) {
//			return product.get();
//		}else {
//			throw new ResourceNotFoundException("Product", "productId",productId);
//		}
		return dao.findById(productId).orElseThrow(()->
		         new ResourceNotFoundException("Product", "productId", productId));
	}

	@Override
	public Product updateProduct(Product product) {
		Optional<Product> Product2= dao.findById(product.getProductId());
		Product existedProduct=Product2.get();
		existedProduct.setProductName(product.getProductName());
		existedProduct.setProductCost(product.getProductCost());
		existedProduct.setProductExpiryDate(product.getProductExpiryDate());
		
		dao.save(existedProduct);
		return existedProduct;
	}

	@Override
	public String deleteAllProduct() {
		dao.deleteAll();
		return "All Product Deleted Successfully";
	}

	@Override
	public String deleteProductById(long productId) {
		dao.deleteById(productId);
		return "Product with Id "+productId+ " deleted Susseccfully";
	}

	@Override
	public String uploadProductSheet(CommonsMultipartFile file,HttpSession session) {
			String filePath1 = session.getServletContext().getRealPath("/WEB-INF/upload");
			System.out.println("=+=+=> "+filePath1);
			String filePath="C:\\springboot project\\ProductRESTfulAPI\\src\\main\\WEB-INF\\upload";
			String fileName = file.getOriginalFilename();
			byte[] data = file.getBytes();
			FileOutputStream fos;
			String message = null;
			if (!file.isEmpty()) {
				try {
					fos = new FileOutputStream(new File(filePath + File.separator + fileName));
					fos.write(data);
					List<Product> productList = readProductExcelSheet(filePath + File.separator + fileName);
				    message="File Uploaded Successfully";
				} catch (Exception e) {
					e.printStackTrace();
				} finally {

				}
			} else {
				message = "first select file and then upload";
			}

			return message;
		}

		public List<Product> readProductExcelSheet(String file) {
			List<Product> productList = new ArrayList<Product>();
			DataFormatter formatter = new DataFormatter();
			try {

				FileInputStream fis = new FileInputStream(file);
				Workbook workbook = new XSSFWorkbook(fis);
				Sheet sheet = workbook.getSheetAt(0);
				Iterator<Row> rows = sheet.rowIterator();
				Product product = null;
				while (rows.hasNext()) {
					Thread.sleep(5);
					product = new Product();
					String timeStamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new java.util.Date());
					long id=Long.parseLong(timeStamp);
					product.setProductId(id);
					Row row = rows.next();
					Iterator<Cell> cells = row.cellIterator();

					while (cells.hasNext()) {

						Cell cell = cells.next();
						int column = cell.getColumnIndex();

						switch (column) {
						case 0: {
							product.setProductName(cell.getStringCellValue());
							break;
						}
						case 1: {
                            product.setProductCost(cell.getNumericCellValue());
							break;
						}        
						case 2: {
							String productexpiry = formatter.formatCellValue(cell);
							product.setProductExpiryDate(productexpiry);
							break;
						}
						}
					}
					productList.add(product);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
         dao.saveAll(productList);
			return productList;
		}
	}


