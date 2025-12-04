package com.bnp.bookstore.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
	
	private final UserService userService;
	
	private final CartService cartService;
	
	// TODO: Use userId to fetch orders by User
	public Page<Order> getOrders(int page, int pageSize, Long userId) {
		
		Pageable pageable = PageRequest.of(page, pageSize);
		
		return orderRepository.findAll(pageable);
	}
	
	public Order createOrder(OrderRequest request, Long userId) throws ResourceNotFoundException {
	
		User user = userService.getUserById(userId);
		
		// Complete the cart before creating the order
		Cart cart = cartService.completeUserCart(request.cartId());
		
		return orderRepository.save(Order.builder()
				.cart(cart)
				.user(user)
				.paymentComplete(false)
				.build());
	}
	
	public Order updateOrder(OrderRequest request, Long userId) throws ResourceNotFoundException {
		
		User user = userService.getUserById(userId);
		
		Optional<Order> order = orderRepository.findByUserAndPaymentComplete(user, false);
		
		if(order.isEmpty())
			throw new ResourceNotFoundException("Active order not found for user");
		
		// Complete the order
		Order existingOrder = order.get();
		existingOrder.setPaymentComplete(true);
		
		return orderRepository.save(existingOrder);
	}
}
