package com.bnp.bookstore.services;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.bnp.bookstore.entities.Book;
import com.bnp.bookstore.entities.Cart;
import com.bnp.bookstore.entities.CartItem;
import com.bnp.bookstore.entities.User;
import com.bnp.bookstore.exceptions.ResourceNotFoundException;
import com.bnp.bookstore.exceptions.ResourceExistException;
import com.bnp.bookstore.models.CartRequest;
import com.bnp.bookstore.repositories.CartRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CartService {

	private final CartRepository cartRepository;
	
	private final UserService userService;
	
	private final BookService bookService;
	
	public Cart getCartByUser(Long userId) throws ResourceNotFoundException {
		
		User user = userService.getUserById(userId);
		
		Optional<Cart> cart = cartRepository.findByUserAndIsComplete(user, false);
		
		if(cart.isEmpty())
			throw new ResourceNotFoundException("Cart not found for the user");
		
		return cart.get();
	}
	
	public Cart createUserCart(CartRequest request, Long userId) throws ResourceExistException, ResourceNotFoundException {
		
		User user = userService.getUserById(userId);
		
		Optional<Cart> cart = cartRepository.findByUserAndIsComplete(user, false);
		
		if(cart.isPresent())
			throw new ResourceExistException("Cart already exists for the user");
		
		Book book = bookService.getBook(request.bookId());
		
		return cartRepository.save(Cart.builder()
				.isComplete(false)
				.user(user)
				.cartItems(Set.of(CartItem.builder()
						.book(book)
						.purchasedQuantity(request.purchaseQuantity())
						.build()))
				.build());
	}
	
	public Cart updateUserCart(CartRequest request, Long userId) throws ResourceNotFoundException {
		
		Cart existingCart = this.getCartByUser(userId);
		
		Book book = bookService.getBook(request.bookId());
		
		Set<CartItem> items = existingCart.getCartItems();
		
		// Remove item if quantity 0
		if(request.purchaseQuantity() == 0)
			items.removeIf(i -> i.getBook().getId() == book.getId());
		else
			items.stream().filter(i -> i.getBook().getId() == book.getId()).findAny().ifPresent(i -> i.setPurchasedQuantity(request.purchaseQuantity()));
		
		existingCart.setCartItems(items);
		
		return cartRepository.save(existingCart);
	}
	
	public Cart completeUserCart(Long userId) throws ResourceNotFoundException {
		
		Cart existingCart = this.getCartByUser(userId);
		
		existingCart.setIsComplete(true);
		
		return cartRepository.save(existingCart);
	}
	
	public void deleteUserCart(Long userId) throws ResourceNotFoundException {
		
		Cart existingCart = this.getCartByUser(userId);
		
		if(existingCart == null)
			throw new ResourceNotFoundException("Cart not found for the user");
		
		cartRepository.delete(existingCart);
	}
}
