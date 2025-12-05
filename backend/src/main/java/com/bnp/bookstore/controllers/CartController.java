package com.bnp.bookstore.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

@RestController
@AllArgsConstructor
public class CartController {

	private final CartService cartService;
	
	@GetMapping("/carts")
	public ResponseEntity<Cart> getCartByUser() throws ResourceNotFoundException {
		
		Optional<Cart> cart = cartService.getCartByUser();
		
		if(cart.isEmpty())
			return new ResponseEntity<Cart>(HttpStatus.NOT_FOUND);
	
		return new ResponseEntity<Cart>(cart.get(), HttpStatus.OK);
		
	}
	
	@PostMapping("/carts")
	public ResponseEntity<Cart> createUserCart(@RequestBody CartRequest request) throws ResourceExistException, ResourceNotFoundException {

		Cart cart = cartService.createUserCart(request);
		
		return new ResponseEntity<Cart>(cart, HttpStatus.CREATED);
	}
	
	@PutMapping("/carts")
	public ResponseEntity<Cart> updateUserCart(@RequestBody CartRequest request) throws ResourceExistException, ResourceNotFoundException {

		Cart cart = cartService.updateUserCart(request);
		
		return new ResponseEntity<Cart>(cart, HttpStatus.OK);
	}
	
	@DeleteMapping("/carts")
	public ResponseEntity<String> deleteUserCart() throws ResourceExistException, ResourceNotFoundException {

		cartService.deleteUserCart();
		
		return new ResponseEntity<String>("User Cart removed successfully", HttpStatus.OK);
	}
}
