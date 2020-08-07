package com.uvaan.sampleprojectapi.service;

import java.util.Optional;

import com.uvaan.sampleprojectapi.domain.User;
import com.uvaan.sampleprojectapi.param.UserParam;



public interface UserService {

	Optional<User> getUserById(Long id);
	User createUser(UserParam userParam);
	void deleteUser(Long id);
	User updateUser(UserParam userParam);
	
	User findByEmail(String email);
	
	
	
	

}
