package com.uvaan.sampleprojectapi.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uvaan.sampleprojectapi.domain.User;




@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	User findByEmail(String email);

	
}