package com.dev.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dev.models.ExoUser;
import com.dev.models.dtos.LoginRequest;
import com.dev.repos.ExoUserRepo;

import lombok.extern.slf4j.Slf4j;

@Service @Slf4j
public class ExoUserService {

	private ExoUserRepo userRepo;
	
	public ExoUserService(ExoUserRepo userRepo) {
		this.userRepo = userRepo;
	}
	
	public Optional<ExoUser> findById(int id) {
		return userRepo.findById(id);
	}
	
	public ExoUser verifyAuth(LoginRequest body) {
		log.info("Verifying Login for user "+ body.getUsername()+"");
		
		List<ExoUser> userList = userRepo.findByUsername(body.getUsername());
	
		if(userList.isEmpty()) {
		log.info("Login authentication failed for username: "+ body.getUsername()+"");
		return null;
		} else if(!userList.get(0).getPassword().equals(body.getPassword())) {	
			log.info(body.getPassword());

			log.info("Login authentication failed for password: "+body.getPassword()+"");
			return null;
		} 			

		log.info("Login complete");
		return new ExoUser(userList.get(0).getId(), userList.get(0).getUsername(), userList.get(0).getPassword(), userList.get(0).getImg(), userList.get(0).getPost());	
		
	}
	
	public boolean verifyRegistration(ExoUser user) {
		log.info("Checking For unique username: " + user.getUsername());

		boolean check;
		List<ExoUser> list = userRepo.findByUsername(user.getUsername());
		if(list.isEmpty()) {
		userRepo.save(user);
		check = true;
		
		log.info("Check passed: " + user.getUsername() +" is unique");

		} else {
			check = false;
		log.info("Check failed: " + user.getUsername() +" is not unique");
		}
		
		return check;
	}
}
