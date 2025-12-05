package com.bnp.bookstore.config.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bnp.bookstore.entities.User;
import com.bnp.bookstore.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Optional<User> user = userRepository.findByEmail(email);
		
		if(user.isEmpty())
			throw new UsernameNotFoundException(String.format("User not found with email %s", email));
		
		return user.get();
	}

}
