package com.eb1.JWTConfiguration;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eb1.Model.User;
import com.eb1.Service.UserServices.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserService userService;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
		try {
			User user = userService.findByMobile(mobile);
			return UserPrincipal.create(user);
		} catch (Exception e) {
			throw new UsernameNotFoundException("User not found with Mobile : " + mobile);
		}
	}

	// This method is used by JWTAuthenticationFilter
	@Transactional
	public UserDetails loadUserById(Long id) {
		try {
			User user = userService.getUserDetailById(id);
			return UserPrincipal.create(user);
		} catch (Exception e) {
			throw new UsernameNotFoundException("User not found with id : " + id);
		}
	}



}
