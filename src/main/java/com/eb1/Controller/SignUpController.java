package com.eb1.Controller;

import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eb1.Controller.RequestPOJO.ApiResponse;
import com.eb1.Controller.RequestPOJO.LoginRequest;
import com.eb1.JWTConfiguration.AuthManager;
import com.eb1.JWTConfiguration.JwtTokenProvider;
import com.eb1.Model.User;
import com.eb1.Service.UserServices.UserService;

@RestController
@RequestMapping(path = "api/signup")
public class SignUpController {

	@Autowired
	UserService userservice;

	@RequestMapping("user")
	public ResponseEntity<?> userLogin(@RequestBody HashMap<String, String> signupRequest) {
		try {

			User user = userservice.signUpUser(signupRequest);
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), ""));
		}
	}
}
