package com.revature.controllers;

import java.util.List;
import java.util.Optional;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.dtos.EntryRequest;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Entry;
import com.revature.models.User;
import com.revature.services.EntryService;
import com.revature.services.UserService;

@RestController
@RequestMapping("/api/entries")
public class EntryController {
	
	private final EntryService entryService;
	private final UserService userService;
	
	public EntryController (EntryService entryService, UserService userService) {
		this.entryService=entryService;
		this.userService=userService;
	}
	
	//delete
	@DeleteMapping("/{id}")
	public ResponseEntity<Entry> deleteEntry(@PathVariable("id") int id){
		Optional<Entry> optionalEntry = entryService.findById(id);
		
		if (!optionalEntry.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		entryService.delete(id);
		
		return ResponseEntity.ok(optionalEntry.get());
	}
	
	//save
	@PostMapping("/{id}")
	public ResponseEntity<Entry> saveEntry(@PathVariable("id") int id, EntryRequest entryRequest){
		Optional<User> optionalUser = userService.findById(id);
		
		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException(id);
		}
		
		return ResponseEntity.ok(entryService.add(entryRequest, id));
	}
	
	//get entries by id
	@GetMapping("/{id}")
	public ResponseEntity<List<Entry>> getEntries(@PathVariable("id") int id){
		Optional<User> optionalUser = userService.findById(id);
		
		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException(id);
		}
		
		return ResponseEntity.ok(entryService.findByOwner(id));
	}
}
