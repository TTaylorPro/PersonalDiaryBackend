package com.revature.exceptions;

public class EntryNotFoundException extends RuntimeException{
	public EntryNotFoundException() {
		
	}
	
	public EntryNotFoundException(String message) {
		super(message);
	}
	
	public EntryNotFoundException(int entryId) {
		super(String.format("No entry found with id %d", entryId));
	}
}
