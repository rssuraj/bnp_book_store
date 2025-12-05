package com.bnp.bookstore.services;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.bnp.bookstore.config.security.SecurityConfig;
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
	
	private final BookService bookService;
	
	public Optional<Cart> getCartByUser() throws ResourceNotFoundException {
		
		User user = SecurityConfig.getAuthenticatedUser();
		
		return cartRepository.findByUserAndIsComplete(user, false);
	}
	
	public Cart createUserCart(CartRequest request) throws ResourceExistException, ResourceNotFoundException {
		
		User user = SecurityConfig.getAuthenticatedUser();
		
		Optional<Cart> cart = cartRepository.findByUserAndIsComplete(user, false);
		
		if(cart.isPresent())
			throw new ResourceExistException("Cart already exists for the user");
		
		Book book = bookService.getBook(request.bookId());
		
		Cart newCart = new Cart();
		newCart.setIsComplete(false);
		newCart.setUser(user);
		CartItem newCartItem = new CartItem();
		newCartItem.setBook(book);
		newCartItem.setPurchasedQuantity(request.purchaseQuantity());
		newCart.setCartItems(Set.of(newCartItem));
		
		return cartRepository.save(newCart);
	}
	
	public Cart updateUserCart(CartRequest request) throws ResourceNotFoundException {
		
		Optional<Cart> existingCart = this.getCartByUser();
		
		Book book = bookService.getBook(request.bookId());
		
		Cart updatedCart = existingCart.get();
		
		Set<CartItem> items = updatedCart.getCartItems();
		
		// Remove item if quantity 0
		if(request.purchaseQuantity() == 0)
			items.removeIf(i -> i.getBook().getId() == book.getId());
		else
			items.stream().filter(i -> i.getBook().getId() == book.getId()).findAny().ifPresent(i -> i.setPurchasedQuantity(request.purchaseQuantity()));
		
		updatedCart.setCartItems(items);
		
		return cartRepository.save(updatedCart);
	}
	
	public Cart completeUserCart() throws ResourceNotFoundException {
		
		Optional<Cart> existingCart = this.getCartByUser();
		
		if(existingCart.isEmpty())
			throw new ResourceNotFoundException("Cart not found for the user");
		
		Cart updatedCart = existingCart.get();
		updatedCart.setIsComplete(true);
		
		return cartRepository.save(updatedCart);
	}
	
	public void deleteUserCart() throws ResourceNotFoundException {
		
		Optional<Cart> existingCart = this.getCartByUser();
		
		if(existingCart.isEmpty())
			throw new ResourceNotFoundException("Cart not found for the user");
		
		cartRepository.delete(existingCart.get());
	}
}
