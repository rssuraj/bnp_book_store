package com.bnp.bookstore.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bnp.bookstore.exceptions.ResourceExistException;
import com.bnp.bookstore.exceptions.ResourceNotFoundException;
import com.bnp.bookstore.models.CartRequest;
import com.bnp.bookstore.models.CartResponse;
import com.bnp.bookstore.services.CartService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class CartController {

	private final CartService cartService;
	
	@GetMapping("/carts")
	public ResponseEntity<CartResponse> getCartByUser() throws ResourceNotFoundException {
		
		CartResponse cart = cartService.getCartByUser();
		
		if(cart.cart() == null)
			return new ResponseEntity<CartResponse>(cart, HttpStatus.NOT_FOUND);
	
		return new ResponseEntity<CartResponse>(cart, HttpStatus.OK);
		
	}
	
	@PostMapping("/carts")
	public ResponseEntity<CartResponse> createUserCart(@RequestBody CartRequest request) throws ResourceExistException, ResourceNotFoundException {

		CartResponse cart = cartService.createUserCart(request);
		
		if(cart.cart() == null)
			return new ResponseEntity<CartResponse>(cart, HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<CartResponse>(cart, HttpStatus.CREATED);
	}
	
	@PutMapping("/carts")
	public ResponseEntity<CartResponse> updateUserCart(@RequestBody CartRequest request) throws ResourceExistException, ResourceNotFoundException {

		CartResponse cart = cartService.updateUserCart(request);
		
		return new ResponseEntity<CartResponse>(cart, HttpStatus.OK);
	}
}
