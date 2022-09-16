package com.revature.services;

import java.util.Optional;

import com.revature.dtos.Credentials;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.repositories.UserRepository;

public class UserService {
	
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public Optional<User> findByCredentials(Credentials cred){
		Optional<User> optionalUser = userRepository.FindByUsernameAndPassword(cred.getUsername(), cred.getPassword());
		
		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException(String.format("No user found with username %s", cred.getUsername()));
		}
		return optionalUser;
	}
	
	public Optional<User> findByUsername(Credentials cred){
		Optional<User> optionalUser = userRepository.FindByUsername(cred.getUsername());
		
		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException(String.format("No user found with username %s", cred.getUsername()));
		}
		return optionalUser;
	}
	
	public Optional<User> findById(int id) {
		Optional<User> optionalUser = userRepository.findById(id);

		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException(String.format("No user found with ID %d", id));
		}

		return optionalUser;
	}
	
	public User save(User user) {
		return userRepository.save(user);
	}
	
	
	//findByCredentials for logging in
	//findById for getting entries
	//register using save
	//password recovery using save and findByCredentials
	

}
