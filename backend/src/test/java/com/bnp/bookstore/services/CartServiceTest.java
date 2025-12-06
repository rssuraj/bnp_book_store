package com.bnp.bookstore.services;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.bnp.bookstore.config.security.SecurityConfig;
import com.bnp.bookstore.entities.*;
import com.bnp.bookstore.exceptions.*;
import com.bnp.bookstore.models.*;
import com.bnp.bookstore.repositories.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

class CartServiceTest {

    @Mock
    private CartRepository cartRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private BookService bookService;

    @InjectMocks
    private CartService cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCartByUser_NoCart_ReturnsEmptyResponse() throws ResourceNotFoundException {
        // Setup mocks
    	User user = new User();
    	MockedStatic<SecurityConfig> config = Mockito.mockStatic(SecurityConfig.class);
    	config.when(SecurityConfig::getAuthenticatedUser).thenReturn(user);
        when(cartRepository.findByUserAndIsComplete(user, false)).thenReturn(Optional.empty());

        CartResponse response = cartService.getCartByUser();
        assertNull(response.cart());
        assertNull(response.cartItems());
        
        config.close();
    }

    @Test
    void testCreateUserCart_NewCart_Success() throws Exception, ResourceExistException, ResourceNotFoundException {
        Book book = new Book();
        CartRequest request = new CartRequest(1L, 2L);
        User user = new User();
        MockedStatic<SecurityConfig> config = Mockito.mockStatic(SecurityConfig.class);
    	config.when(SecurityConfig::getAuthenticatedUser).thenReturn(user);
        when(cartRepository.findByUserAndIsComplete(user, false)).thenReturn(Optional.empty());
        when(bookService.getBook(1L)).thenReturn(book);

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setIsComplete(false);
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setPurchasedQuantity(2L);
        cartItem.setCart(cart);
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);

        CartResponse response = cartService.createUserCart(request);
        assertNotNull(response.cart());
        assertEquals(1, response.cartItems().size());
        
        config.close();
    }
}

