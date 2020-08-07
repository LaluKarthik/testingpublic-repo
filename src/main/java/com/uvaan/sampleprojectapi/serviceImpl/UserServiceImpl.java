package com.uvaan.sampleprojectapi.serviceImpl;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.uvaan.sampleprojectapi.domain.User;
import com.uvaan.sampleprojectapi.exception.ResourceNotFoundException;
import com.uvaan.sampleprojectapi.param.UserParam;
import com.uvaan.sampleprojectapi.respository.UserRepository;
import com.uvaan.sampleprojectapi.service.UserService;
import com.uvaan.sampleprojectapi.utils.UserUtils;

@Transactional
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	UserUtils userUtils;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Optional<User> getUserById(Long id) {
		Optional<User> a = userRepository.findById(id);
		return a;
	}

	@Override
	public User createUser(UserParam userParam) {

		User user = null;

		user = userRepository.save(getUserByParams(userParam, null));

		return user;
	}

	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);

	}

	@Override
	public User updateUser(UserParam userParam) {
		User user = getUserById(userParam.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userParam.getId()));
		try {

			user = getUserByParams(userParam, user);
			user = userRepository.save(user);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException(
					"User is already exist for selected the mobile & countryCode,Please provide different valid fields.");
		}
		return user;
	}

	private User getUserByParams(UserParam userParam, User dbUser) {
		User user;
		if (null == dbUser) {
			user = new User();

			user.setCreatedDate(userParam.getCreatedDate());

		} else {
			user = dbUser;
			user.setUpdatedDate(userParam.getUpdatedDate());
		}

		user.setEmail(userParam.getEmail());
		user.setPassword(bCryptPasswordEncoder.encode(userParam.getPassword()));

		user.setCreatedBy(userParam.getCreatedBy());
		user.setUpdatedBy(userParam.getUpdatedBy());

		return user;
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

}
