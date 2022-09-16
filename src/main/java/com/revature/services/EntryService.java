package com.revature.services;

import java.util.List;
import java.util.Optional;

import com.revature.dtos.EntryRequest;
import com.revature.exceptions.EntryNotFoundException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Entry;
import com.revature.models.User;
import com.revature.repositories.EntryRepository;

public class EntryService {
	
	private final EntryRepository entryRepository;
	private final UserService userService;
	
	public EntryService(EntryRepository entryRepository, UserService userService) {
		this.entryRepository = entryRepository;
		this.userService = userService;
	}
	
	public List<Entry> findByOwner(int userId){
		Optional<User> optionalUser = this.userService.findById(userId);
		if (optionalUser.isPresent()) {
			return entryRepository.findByUser(optionalUser.get());
		} else {
			throw new UserNotFoundException(userId);
		}
	}
	
	public Optional<Entry> findById(int entryId){
		Optional<Entry> optionalEntry = this.entryRepository.findById(entryId);
		if (optionalEntry.isPresent()) return optionalEntry;
		
		else {
			throw new EntryNotFoundException(entryId);
		}
	}
	
	public Entry add(EntryRequest entryRequest, int userId) {
		Optional<User> optionalUser = userService.findById(userId);
		
		if(optionalUser.isPresent()) {
			Entry entry = new Entry();
			entry.setUser(optionalUser.get());
			
			entry.setId(entryRequest.getId());
			entry.setName(entryRequest.getName());
			entry.setContent(entryRequest.getContent());
			
			return entryRepository.save(entry);
			
		} else {
			throw new UserNotFoundException();
		}
		
	}
	
	public void delete(int entryId) {
		entryRepository.deleteById(entryId);
	}
	
	
	//FindByOwner
	
	//add/save
	
	//delete
	
	
}
