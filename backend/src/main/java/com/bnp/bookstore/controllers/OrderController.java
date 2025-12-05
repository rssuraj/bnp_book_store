package com.bnp.bookstore.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bnp.bookstore.entities.Order;
import com.bnp.bookstore.exceptions.ResourceNotFoundException;
import com.bnp.bookstore.models.OrderRequest;
import com.bnp.bookstore.services.OrderService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class OrderController {

	private final OrderService orderService;
	
	@GetMapping("/orders")
	public ResponseEntity<Page<Order>> getOrders(
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "10") int pageSize) {
		
		Page<Order> orders = orderService.getOrders(page, pageSize);
		
		return new ResponseEntity<Page<Order>>(orders, HttpStatus.OK);
	}
	
	@PostMapping("/orders")
	public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request) throws ResourceNotFoundException {

		Order order = orderService.createOrder(request);
		
		return new ResponseEntity<Order>(order, HttpStatus.CREATED);
	}
	
	@PutMapping("/orders/{orderId}")
	public ResponseEntity<Order> updateOrder(@PathVariable Long orderId, @RequestBody OrderRequest request) throws ResourceNotFoundException {

		Order order = orderService.updateOrder(request, orderId);
		
		return new ResponseEntity<Order>(order, HttpStatus.OK);
	}
}
