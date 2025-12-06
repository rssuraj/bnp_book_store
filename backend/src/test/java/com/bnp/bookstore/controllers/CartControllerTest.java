package com.bnp.bookstore.controllers;

import com.bnp.bookstore.entities.Cart;
import com.bnp.bookstore.exceptions.ResourceExistException;
import com.bnp.bookstore.exceptions.ResourceNotFoundException;
import com.bnp.bookstore.models.CartRequest;
import com.bnp.bookstore.models.CartResponse;
import com.bnp.bookstore.services.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartControllerTest {

    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCartByUser_returnsCart_whenCartExists() throws ResourceNotFoundException {
        CartResponse response = mock(CartResponse.class);
        
        when(response.cart()).thenReturn(new Cart());
        when(cartService.getCartByUser()).thenReturn(response);

        ResponseEntity<CartResponse> result = cartController.getCartByUser();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void getCartByUser_returnsNotFound_whenCartIsNull() throws ResourceNotFoundException {
        CartResponse response = mock(CartResponse.class);
        when(response.cart()).thenReturn(null);
        when(cartService.getCartByUser()).thenReturn(response);

        ResponseEntity<CartResponse> result = cartController.getCartByUser();

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void createUserCart_returnsCreated_whenCartCreated() throws ResourceExistException, ResourceNotFoundException {
        CartRequest request = mock(CartRequest.class);
        CartResponse response = mock(CartResponse.class);
        when(response.cart()).thenReturn(new Cart());
        when(cartService.createUserCart(request)).thenReturn(response);

        ResponseEntity<CartResponse> result = cartController.createUserCart(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void createUserCart_returnsBadRequest_whenCartIsNull() throws ResourceExistException, ResourceNotFoundException {
        CartRequest request = mock(CartRequest.class);
        CartResponse response = mock(CartResponse.class);
        when(response.cart()).thenReturn(null);
        when(cartService.createUserCart(request)).thenReturn(response);

        ResponseEntity<CartResponse> result = cartController.createUserCart(request);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void updateUserCart_returnsOk_whenCartUpdated() throws ResourceExistException, ResourceNotFoundException {
        CartRequest request = mock(CartRequest.class);
        CartResponse response = mock(CartResponse.class);
        when(cartService.updateUserCart(request)).thenReturn(response);

        ResponseEntity<CartResponse> result = cartController.updateUserCart(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }
}
