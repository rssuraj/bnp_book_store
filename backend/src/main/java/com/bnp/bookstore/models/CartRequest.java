package com.bnp.bookstore.models;

public record CartRequest(Long bookId, Long purchaseQuantity) {}
