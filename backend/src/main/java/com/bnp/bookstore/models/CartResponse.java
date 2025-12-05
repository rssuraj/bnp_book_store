package com.bnp.bookstore.models;

import java.util.Set;

import com.bnp.bookstore.entities.Cart;
import com.bnp.bookstore.entities.CartItem;

public record CartResponse(Cart cart, Set<CartItem> cartItems) {}
