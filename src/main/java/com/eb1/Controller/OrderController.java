package com.eb1.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eb1.Controller.RequestPOJO.ApiResponse;
import com.eb1.Model.AddtoCart;
import com.eb1.Model.CheckoutCart;
import com.eb1.Model.Products;
import com.eb1.Repository.CheckoutRepository;
import com.eb1.Repository.OrderRepository;
import com.eb1.Service.CartService.CartService;
import com.eb1.Service.ProductService.ProductServices;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "api/order")
public class OrderController {

	@Autowired
	CartService cartService;
	ProductServices proService;
	
	@Autowired
	CheckoutRepository repo;

	@RequestMapping("checkout_order")
	public ResponseEntity<?> checkout_order(@RequestBody HashMap<String, String> addCartRequest) {
		try {
			
			long user_Id = Long.parseLong(addCartRequest.get("userId"));
			double total_amt = Double.parseDouble(addCartRequest.get("total_price"));
			if (cartService.checkTotalAmountAgainstCart(total_amt, user_Id)) {
				List<AddtoCart> cartItems = cartService.getCartByUserId(user_Id);
				List<CheckoutCart> tmp = new ArrayList<CheckoutCart>();
				for (AddtoCart addCart : cartItems) {
					String orderId = "" + getOrderId();
					CheckoutCart cart = new CheckoutCart();
					cart.setPayment_type(addCartRequest.get("pay_type"));
					cart.setPrice(total_amt);
					cart.setUser_id(user_Id);
					cart.setOrder_id(orderId);
					cart.setProduct(addCart.getProduct());
					cart.setQty(addCart.getQty());
					cart.setDelivery_address(addCartRequest.get("deliveryAddress"));
					tmp.add(cart);
				}
				cartService.saveProductsForCheckout(tmp);
				return ResponseEntity.ok(new ApiResponse("Order placed successfully", ""));
			} else {
				throw new Exception("Total amount is mismatch");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), ""));
		}
	}

	public int getOrderId() {
		Random r = new Random(System.currentTimeMillis());
		return 10000 + r.nextInt(20000);
	}

	@RequestMapping("getOrdersByUserId")
	public ResponseEntity<?> getOrdersByUserId(@RequestBody HashMap<String, String> ordersRequest) {
		try {
			
			long userId = Long.parseLong(ordersRequest.get("userId"));
			List<CheckoutCart> obj  = cartService.getAllCheckoutByUserId(userId);
			return ResponseEntity.ok().body(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), ""));
		}

	}
	
}
