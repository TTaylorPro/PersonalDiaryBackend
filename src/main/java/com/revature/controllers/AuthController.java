package com.revature.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.dtos.Credentials;
import com.revature.dtos.EntryRequest;
import com.revature.models.Entry;
import com.revature.models.User;
import com.revature.services.EntryService;
import com.revature.services.UserService;
import com.revature.util.JwtTokenManager;

@RestController
@CrossOrigin(origins="*", allowedHeaders="*")
@RequestMapping("api/login")
public class AuthController {
	
	private UserService userService;
	private EntryService entryService;
	private JwtTokenManager tokenManager;
	
	public AuthController (UserService userService, EntryService entryService, JwtTokenManager tokenManager) {
		this.userService = userService;
		this.entryService = entryService;
		this.tokenManager = tokenManager;
	}
	
	@PostMapping("/password")
	public User login(@RequestBody Credentials creds, HttpServletResponse response) {
		Optional<User> user = userService.findByCredentials(creds);
		
		if (user.isPresent()) {
			String token = tokenManager.issueToken(user.get());
			
			response.addHeader("personal-diary-token", token);
			response.addHeader("Access-Control-Expose-Headers", "personal-diary-token");
			response.setStatus(200);
			
			return user.get();
		}
		else {
			response.setStatus(401); // 401 is an UNAUTHORIZED status
			return null;
		}
	}
	
	//Recover Password
	@PostMapping("/questions")
	public User recoverPassword(@RequestBody EntryRequest entryRequest, HttpServletResponse response) {
		Optional<Entry> originalEntry = entryService.findById(entryRequest.getId());
		
		if (originalEntry.isPresent() && originalEntry.get().getContent().equals(entryRequest.getContent())) {
			String token = tokenManager.issueToken(originalEntry.get().getUser());
			
			response.addHeader("personal-diary-token", token);
			response.addHeader("Access-Control-Expose-Headers", "personal-diary-token");
			response.setStatus(200);
			
			return originalEntry.get().getUser();
		}
		
		else {
			response.setStatus(401); // 401 is an UNAUTHORIZED status
			return null;
		}
	}
	
	//Get recovery questions
	@GetMapping()
	public ResponseEntity<List<Entry>> getRecoveryQuestions(@RequestBody Credentials creds){
		Optional<User> user = userService.findByUsername(creds);
		
		if (user.isPresent()) {
			List<Entry> entries = entryService.findByOwner(user.get().getId());
			
			List<Entry> questions = entries.stream()
					.sorted((o1,o2) -> o1.getTime().compareTo(o2.getTime()))
					.limit(2)
					.map(x -> x = new Entry(x.getId(), null, x.getName(), null,  ""))
					.collect(Collectors.toList());
			
			return ResponseEntity.ok(questions);
			
			
		}
		
		else {
			return ResponseEntity.notFound().build();
		}
	}
}
