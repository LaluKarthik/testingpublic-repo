package com.uvaan.sampleprojectapi.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.uvaan.sampleprojectapi.config.JwtTokenUtil;
import com.uvaan.sampleprojectapi.domain.User;
import com.uvaan.sampleprojectapi.param.UserParam;
import com.uvaan.sampleprojectapi.service.JwtUserDetailsService;
import com.uvaan.sampleprojectapi.service.UserService;


@RestController
@RequestMapping("/api/auth")
public class AuthController<GenericResponse, StandardStringDigester> {

	@Autowired
	UserController userController;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private UserService userService;
	

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody UserParam userParam) throws Exception {

		authenticate(userParam.getEmail(), userParam.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(userParam.getEmail());
		final String token = jwtTokenUtil.generateToken(userDetails);
		Map<String, String> model = new HashMap<String, String>();
		User user = userService.findByEmail(userParam.getEmail());
		model.put("userId", user.getId().toString());
		model.put("email", user.getEmail());
		model.put("token", token);
		return ResponseEntity.ok(model);
	}


	private void authenticate(String email, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
