package com.eb1.Controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eb1.Controller.RequestPOJO.ApiResponse;
import com.eb1.Model.AddtoCart;
import com.eb1.Repository.AddtoCartRepository;
import com.eb1.Service.CartService.CartService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/addtocart")
public class AddtoCartController {

	@Autowired
	CartService cartService;

	@Autowired 
	AddtoCartRepository cartRepo;
	@RequestMapping("addProduct")
	public ResponseEntity<?> addCartwithProduct(@RequestBody HashMap<String, String> addCartRequest) {
		try {
			long userId = Long.parseLong(addCartRequest.get("userId"));
			long productId = Long.parseLong(addCartRequest.get("productId"));
			int qty = Integer.parseInt(addCartRequest.get("qty"));
			double price = Double.parseDouble(addCartRequest.get("price"));
			List<AddtoCart> obj = cartService.addCartbyUserIdAndProductId(productId, userId, qty, price);
			return ResponseEntity.ok(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), ""));
		}

	}

	@RequestMapping("updateQtyForCart")
	public ResponseEntity<?> updateQtyForCart(@RequestBody HashMap<String, String> addCartRequest) {
		try {
			
			long cartId = Long.parseLong(addCartRequest.get("cartId"));
			long userId = Long.parseLong(addCartRequest.get("userId"));
			int qty = Integer.parseInt(addCartRequest.get("qty"));
			double price = Double.parseDouble(addCartRequest.get("price"));
			cartService.updateQtyByCartId(cartId, qty, price);
			List<AddtoCart> obj = cartService.getCartByUserId(userId);
			return ResponseEntity.ok(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), ""));
		}

	}

	@RequestMapping("removeProductFromCart")
	public ResponseEntity<?> removeCartwithProductId(@RequestBody HashMap<String, String> removeCartRequest) {
		try {
			
			List<AddtoCart> obj = cartService.removeCartByUserId(Long.parseLong(removeCartRequest.get("cartId")),
					Long.parseLong(removeCartRequest.get("userId")));
			return ResponseEntity.ok(obj);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), ""));
		}
	}
	
	@RequestMapping("removeAllProductFromCart")
	public ResponseEntity<?> removeAllProductFromCart(@RequestBody HashMap<String, String> removeCartRequest) {
		try {
			
			List<AddtoCart> obj = cartService.removeAllCartByUserId(Long.parseLong(removeCartRequest.get("userId")));
			return ResponseEntity.ok(obj);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), ""));
		}
	}

	@RequestMapping("getCartsByUserId")
	public ResponseEntity<?> getCartsByUserId(@RequestBody HashMap<String, String> getCartRequest) {
		try {
			List<AddtoCart> obj = cartService.getCartByUserId(Long.parseLong(getCartRequest.get("userId")));
			return ResponseEntity.ok(obj);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), ""));
		}
	}
	
	@RequestMapping("getTotalAmount")
	public ResponseEntity<?> getTotalAmount(@RequestBody HashMap<String, String> getAmountRequest){
		try {
			return ResponseEntity.ok(cartRepo.getTotalAmountByUserId(Long.parseLong(getAmountRequest.get("userId"))));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), ""));
		}
	}
}