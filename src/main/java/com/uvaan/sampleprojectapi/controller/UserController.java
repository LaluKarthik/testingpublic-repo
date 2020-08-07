package com.uvaan.sampleprojectapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.uvaan.sampleprojectapi.constants.Constants;
import com.uvaan.sampleprojectapi.domain.ApiResponse;
import com.uvaan.sampleprojectapi.domain.User;
import com.uvaan.sampleprojectapi.exception.ResourceNotFoundException;
import com.uvaan.sampleprojectapi.exception.SampleProjectException;
import com.uvaan.sampleprojectapi.param.UserParam;
import com.uvaan.sampleprojectapi.service.UserService;
import com.uvaan.sampleprojectapi.validations.UserValidation;


@RestController
@RequestMapping(value = "/userService")
public class UserController {

	Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserValidation userValidator;

	@Autowired
	UserService userService;

	@RequestMapping(path = "/getUserById/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ApiResponse getUserById(@PathVariable("id") Long id) {
		User user = userService.getUserById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		return new ApiResponse(HttpStatus.OK, Constants.SUCCESS, user);
	}

	@RequestMapping(path = "/createUser", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ApiResponse createUser(@RequestBody UserParam userParam, BindingResult result) {
		Map<String, String> map = new HashMap<String, String>();
		MapBindingResult err = new MapBindingResult(map, UserParam.class.getName());
		userValidator.validate(userParam, err);
		List<ObjectError> list = err.getAllErrors();
		if (list.size() > 0) {
			throw new SampleProjectException(list);
		}
		User user = userService.createUser(userParam);
		return new ApiResponse(HttpStatus.OK, Constants.SUCCESS, user);
	}

	@RequestMapping(path = "/updateUser", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ApiResponse updateUser(@RequestBody UserParam userParam, BindingResult result) {
		Map<String, String> map = new HashMap<String, String>();
		MapBindingResult err = new MapBindingResult(map, UserParam.class.getName());
		userValidator.validateupdate(userParam, err);
		List<ObjectError> list = err.getAllErrors();
		if (list.size() > 0) {
			throw new SampleProjectException(list);
		}
		User user = userService.updateUser(userParam);
		return new ApiResponse(HttpStatus.OK, Constants.SUCCESS, user);
	}

	@RequestMapping(path = "/deleteUser/{id}", method = RequestMethod.POST)
	public ApiResponse deleteUser(@PathVariable("id") Long id) {
		getUserById(id);
		userService.deleteUser(id);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("message", "User deleted Successfully..");
		return new ApiResponse(HttpStatus.OK, Constants.SUCCESS, map);
	}

	@Bean
	public UserValidation getUserValidator() {
		return new UserValidation();
	}

}