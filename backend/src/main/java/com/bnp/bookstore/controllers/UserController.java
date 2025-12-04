package com.bnp.bookstore.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bnp.bookstore.entities.User;
import com.bnp.bookstore.exceptions.ResourceNotFoundException;
import com.bnp.bookstore.services.UserService;

import lombok.AllArgsConstructor;

@RestController("/users")
@AllArgsConstructor
public class UserController {

	private final UserService userService;
	
	@GetMapping("/{userId}")
	public ResponseEntity<User> getUser(@PathVariable Long userId) throws ResourceNotFoundException {
		User user = userService.getUserById(userId);
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
}
