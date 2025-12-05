package com.bnp.bookstore.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import com.bnp.bookstore.config.security.JwtService;
import com.bnp.bookstore.config.security.SecurityConfig;
import com.bnp.bookstore.entities.User;
import com.bnp.bookstore.exceptions.ResourceNotFoundException;
import com.bnp.bookstore.models.LoginRequest;
import com.bnp.bookstore.models.LoginResponse;
import com.bnp.bookstore.models.UserRequest;
import com.bnp.bookstore.services.UserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController {
	
	private final AuthenticationManager authenticationManager;
	
    private final JwtService jwtService;
    
    private final UserService userService;
	
	@GetMapping("/users/profile")
	public ResponseEntity<User> getUser() throws ResourceNotFoundException {
		
		User user = SecurityConfig.getAuthenticatedUser();
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@PostMapping("/users/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.username(),
                    request.password()
            )
        );

        UserDetails user = (UserDetails) authentication.getPrincipal();
        String jwtToken = jwtService.generateToken(user);

        return new ResponseEntity<>(new LoginResponse(jwtToken, "Login successful"), HttpStatus.OK);
    }
	
	@PostMapping("/users/register")
    public ResponseEntity<?> register(@RequestBody UserRequest request) {
        
        User user = userService.createUser(request);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
