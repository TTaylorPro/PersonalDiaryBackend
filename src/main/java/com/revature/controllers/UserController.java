package com.revature.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.dtos.Credentials;
import com.revature.models.Entry;
import com.revature.models.User;
import com.revature.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	private final UserService userService;
	
	public UserController (UserService userService) {
		this.userService = userService;
	}
	
	//Which methods do I need for my features?
	
	//register
	
	@PostMapping
	public ResponseEntity<User> registerUser(@RequestBody Credentials creds){
		User newUser = new User(0, creds.getUsername(), creds.getPassword(), new ArrayList<Entry>());
		
		return ResponseEntity.ok(userService.save(newUser));
	}
	
	//changePassword
	@PutMapping
	public ResponseEntity<User> updatePassword(@RequestBody User user){
		Optional<User> updatedUserOptional = userService.findById(user.getId());
		if(!updatedUserOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		User updatedUser = updatedUserOptional.get();
		
		updatedUser.setPassword(user.getPassword());
		
		return ResponseEntity.ok(userService.save(updatedUser));
	}
	
	
	//
}
