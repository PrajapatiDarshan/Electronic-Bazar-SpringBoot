package com.eb1.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eb1.Model.Category;
import com.eb1.Model.Products;
import com.eb1.Repository.ProductRepository;

@RestController
@CrossOrigin
@RequestMapping(path = "product")
public class ProductController {
	private byte[] bytes;
	@Autowired
	com.eb1.Service.ProductService.ProductServices ProductServices;

	@RequestMapping("getAll")
	public List<Products> getAllPRoducts() {
		return ProductServices.getAllProducts();
	}

	@RequestMapping("getAllCategory")
	public List<Category> getAllCategory() {
		return ProductServices.getAllCategory();
	}

	@RequestMapping("getProductsByCategory")
	public List<Products> getProductsByCategory(@RequestBody HashMap<String, String> request) {
		String category_id = request.get("cat_id");
		return ProductServices.getProductsByCategory(category_id);
	}

	
	@PostMapping("/upload")
	public void uploadImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
		this.bytes = file.getBytes();
	}
}
