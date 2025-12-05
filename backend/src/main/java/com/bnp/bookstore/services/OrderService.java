package com.bnp.bookstore.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bnp.bookstore.config.security.SecurityConfig;
import com.bnp.bookstore.entities.Cart;
import com.bnp.bookstore.entities.Order;
import com.bnp.bookstore.entities.User;
import com.bnp.bookstore.exceptions.ResourceNotFoundException;
import com.bnp.bookstore.models.OrderRequest;
import com.bnp.bookstore.repositories.OrderRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	
	private final CartService cartService;
	
	// TODO: Use userId to fetch orders by User
	public Page<Order> getOrders(int page, int pageSize) {
		
		Pageable pageable = PageRequest.of(page, pageSize);
		
		return orderRepository.findAll(pageable);
	}
	
	public Order createOrder(OrderRequest request) throws ResourceNotFoundException {
	
		User user = SecurityConfig.getAuthenticatedUser();
		
		// Complete the cart before creating the order
		Cart cart = cartService.completeUserCart();
		
		Order newOrder = new Order();
		newOrder.setCart(cart);
		newOrder.setUser(user);
		newOrder.setPaymentComplete(false);
		
		return orderRepository.save(newOrder);
	}
	
	public Order updateOrder(OrderRequest request, Long orderId) throws ResourceNotFoundException {
		
		Optional<Order> order = orderRepository.findById(orderId);
		
		if(order.isEmpty())
			throw new ResourceNotFoundException("Active order not found for user");
		
		// Complete the order
		Order existingOrder = order.get();
		existingOrder.setPaymentComplete(true);
		
		return orderRepository.save(existingOrder);
	}
}
