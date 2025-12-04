package com.bnp.bookstore.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bnp.bookstore.entities.Cart;
import com.bnp.bookstore.exceptions.ResourceExistException;
import com.bnp.bookstore.exceptions.ResourceNotFoundException;
import com.bnp.bookstore.models.CartRequest;
import com.bnp.bookstore.services.CartService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.AllArgsConstructor;

@RestController("/carts")
@AllArgsConstructor
public class CartController {

	private final CartService cartService;
	
	@GetMapping("/{userId}")
	public ResponseEntity<Cart> getCartByUser(@PathVariable Long userId) throws ResourceNotFoundException {
		
		Cart cart = cartService.getCartByUser(userId);
		
		return new ResponseEntity<Cart>(cart, HttpStatus.OK);
	}
	
	@PostMapping("/{userId}")
	public ResponseEntity<Cart> createUserCart(@PathVariable Long userId, @RequestBody CartRequest request) throws ResourceExistException, ResourceNotFoundException {

		Cart cart = cartService.createUserCart(request, userId);
		
		return new ResponseEntity<Cart>(cart, HttpStatus.CREATED);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<Cart> updateUserCart(@PathVariable Long userId, @RequestBody CartRequest request) throws ResourceExistException, ResourceNotFoundException {

		Cart cart = cartService.updateUserCart(request, userId);
		
		return new ResponseEntity<Cart>(cart, HttpStatus.OK);
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<String> deleteUserCart(@PathVariable Long userId) throws ResourceExistException, ResourceNotFoundException {

		cartService.deleteUserCart(userId);
		
		return new ResponseEntity<String>("User Cart removed successfully", HttpStatus.OK);
	}
}
