package com.bnp.bookstore.models;

import jakarta.annotation.Nonnull;

public record CartRequest(
		@Nonnull Long bookId, 
		@Nonnull Long purchaseQuantity
) {}
