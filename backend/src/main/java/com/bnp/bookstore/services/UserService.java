package com.bnp.bookstore.services;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bnp.bookstore.entities.User;
import com.bnp.bookstore.exceptions.ResourceNotFoundException;
import com.bnp.bookstore.models.UserRequest;
import com.bnp.bookstore.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	public User getUserById(Long userId) throws ResourceNotFoundException {
		Optional<User> user = userRepository.findById(userId);
		
		if(user.isEmpty())
			throw new ResourceNotFoundException("User not found");
		
		return user.get();
	}
	
	public User createUser(UserRequest request) {

		User newUser = new User();
        newUser.setEmail(request.email()); 
        newUser.setPassword(passwordEncoder.encode(request.password()));
        newUser.setFirstName(request.firstName());
        newUser.setLastName(request.lastName());
        
		return userRepository.save(newUser);
	}

}
