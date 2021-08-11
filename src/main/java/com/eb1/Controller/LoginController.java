package com.eb1.Controller;

import java.util.HashMap;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eb1.Controller.RequestPOJO.ApiResponse;
import com.eb1.Controller.RequestPOJO.LoginRequest;
import com.eb1.JWTConfiguration.AuthManager;
import com.eb1.JWTConfiguration.JwtTokenProvider;
import com.eb1.JWTConfiguration.UserPrincipal;
import com.eb1.Model.User;
import com.eb1.Service.UserServices.UserService;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class LoginController {

	@Autowired
	UserDetailsService userDetailservice;

	@Autowired
	UserService userservice;

	@Autowired
	AuthManager aMan;

	@Autowired
	JwtTokenProvider tokenProvider;

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping("status")
	public ResponseEntity<?> serverStatus() {
		return new ResponseEntity<>(new ApiResponse("Server is Running. ", ""), HttpStatus.OK);
	}

	@RequestMapping("login/user")
	public ResponseEntity<?> userLogin(@RequestBody LoginRequest loginRequest) {
		try {

			Authentication authentication = aMan.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getMobile(), loginRequest.getPassword()),
					loginRequest);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String token = tokenProvider.generateToken(authentication);
			JSONObject obj = this.getUserResponse(token);
			if (obj == null) {
				throw new Exception("Error while generating Reponse");
			}

			return new ResponseEntity<String>(obj.toString(), HttpStatus.OK);

		} catch (Exception e) {
			logger.info("Error in authenticateUser ", e);
			return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), ""));
		}
	}

	private JSONObject getUserResponse(String token) {

		try {
			User user = userservice.getUserDetailById(_getUserId());
			HashMap<String, String> response = new HashMap<String, String>();
			response.put("user_id", "" + _getUserId());
			response.put("email", user.getEmail());
			response.put("name", user.getName());
			response.put("mobile", user.getMobile());

			JSONObject obj = new JSONObject();

			obj.put("user_profile_details", response);
			obj.put("token", token);
			return obj;
		} catch (Exception e) {
			logger.info("Error in getUserResponse ", e);
		}
		return null;
	}

	private long _getUserId() {
		logger.info("user id vaildating. " + SecurityContextHolder.getContext().getAuthentication());
		UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		logger.info("(LoginController)user id is " + userPrincipal.getId());
		return userPrincipal.getId();
	}
}