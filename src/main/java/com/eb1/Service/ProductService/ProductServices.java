package com.eb1.Service.ProductService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eb1.Model.Category;
import com.eb1.Model.Products;
import com.eb1.Repository.CategoryRepository;
import com.eb1.Repository.ProductRepository;



@Service
public class ProductServices {

	@Autowired
	ProductRepository productRepo;
	@Autowired
	CategoryRepository cateRepo;
	
	public List<Products>getAllProducts(){
		return productRepo.findAll();
	}
	public List<Products>getProductsByCategory(String product_id){
		return productRepo.getByCategoryId(product_id);
	}
	
	public List<Category>getAllCategory(){
		return cateRepo.findAll();
	}
	
	public Products getProductsById(long productId) throws Exception {
		return productRepo.findById(productId).orElseThrow(() ->new Exception("Product is not found"));
	}
}