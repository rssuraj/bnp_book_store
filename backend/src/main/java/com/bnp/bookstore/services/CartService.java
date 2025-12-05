package com.bnp.bookstore.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bnp.bookstore.config.security.SecurityConfig;
import com.bnp.bookstore.entities.Book;
import com.bnp.bookstore.entities.Cart;
import com.bnp.bookstore.entities.CartItem;
import com.bnp.bookstore.entities.User;
import com.bnp.bookstore.exceptions.ResourceNotFoundException;
import com.bnp.bookstore.exceptions.ResourceExistException;
import com.bnp.bookstore.models.CartRequest;
import com.bnp.bookstore.models.CartResponse;
import com.bnp.bookstore.repositories.CartItemRepository;
import com.bnp.bookstore.repositories.CartRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CartService {

	private final CartRepository cartRepository;
	
	private final CartItemRepository cartItemRepository;
	
	private final BookService bookService;
	
	public CartResponse getCartByUser() throws ResourceNotFoundException {
		
		User user = SecurityConfig.getAuthenticatedUser();
		
		Optional<Cart> cart = cartRepository.findByUserAndIsComplete(user, false);
		
		if(cart.isEmpty())
			return new CartResponse(null, null);
		
		Set<CartItem> items = cartItemRepository.findByCartId(cart.get().getId());
		
		return new CartResponse(cart.get(), items);
	}
	
	public CartResponse createUserCart(CartRequest request) throws ResourceExistException, ResourceNotFoundException {
		
		User user = SecurityConfig.getAuthenticatedUser();
		
		Optional<Cart> cart = cartRepository.findByUserAndIsComplete(user, false);
		
		if(cart.isPresent())
			return new CartResponse(null, null); 
		
		Book book = bookService.getBook(request.bookId());
		
		Cart newCart = new Cart();
		newCart.setIsComplete(false);
		newCart.setUser(user);
		newCart = cartRepository.save(newCart);
		
		CartItem newCartItem = new CartItem();
		newCartItem.setBook(book);
		newCartItem.setPurchasedQuantity(request.purchaseQuantity());
		newCartItem.setCart(newCart);
		newCartItem = cartItemRepository.save(newCartItem);
		
		
		return new CartResponse(newCart, Set.of(newCartItem));
	}
	
	public CartResponse updateUserCart(CartRequest request) throws ResourceNotFoundException {
		
		CartResponse existingCart = this.getCartByUser();
		
		Book book = bookService.getBook(request.bookId());
		
		Cart updatedCart = existingCart.cart();
		
		Set<CartItem> items = existingCart.cartItems();
		
		CartItem cI = items.stream().filter(i -> i.getBook().getId() == book.getId()).findFirst().orElse(null);
		
		if(cI != null) {
		
			// Remove item if quantity 0
			if(request.purchaseQuantity() == 0)
				items.removeIf(i -> i.getBook().getId() == book.getId());
			else
				items.stream().filter(i -> i.getBook().getId() == book.getId()).findAny().ifPresent(i -> i.setPurchasedQuantity(request.purchaseQuantity()));
		} else {
			CartItem newCartItem = new CartItem();
			newCartItem.setBook(book);
			newCartItem.setPurchasedQuantity(request.purchaseQuantity());
			newCartItem.setCart(updatedCart);
			items.add(newCartItem);
		}
		
		// Delete Cart if items are 0
		if(CollectionUtils.isEmpty(items)) {
			cartRepository.delete(existingCart.cart());
			return new CartResponse(null, Set.of());
		} else {
			
			List<CartItem> udpatedItems = cartItemRepository.saveAll(items);
			return new CartResponse(updatedCart, new HashSet<CartItem>(udpatedItems));
		}
	}
	
	public Cart completeUserCart() throws ResourceNotFoundException {
		
		CartResponse existingCart = this.getCartByUser();
		
		Cart updatedCart = existingCart.cart();
		updatedCart.setIsComplete(true);
		
		return cartRepository.save(updatedCart);
	}
}
