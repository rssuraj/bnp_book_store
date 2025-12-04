package com.bnp.bookstore.models;

public record OrderRequest(Long cartId, Boolean paymentComplete) {}
