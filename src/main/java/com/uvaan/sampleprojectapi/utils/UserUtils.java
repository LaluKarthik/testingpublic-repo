package com.uvaan.sampleprojectapi.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.uvaan.sampleprojectapi.service.UserService;

@Component
public class UserUtils {

	@Autowired
	UserService userService;

	public UserDetails getLogedInUserDetails() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (UserDetails) authentication.getPrincipal();
	}

	com.uvaan.sampleprojectapi.domain.User getLogedInUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		com.uvaan.sampleprojectapi.domain.User user = (com.uvaan.sampleprojectapi.domain.User) authentication
				.getPrincipal();
		return userService.findByEmail(user.getEmail());
	}
}
