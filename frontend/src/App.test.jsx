import { describe, it, expect, vi, beforeEach } from 'vitest';
import { render, screen, waitFor, fireEvent } from '@testing-library/react';
import App from './App';
import * as api from './services/api';

vi.mock('./services/api');

describe('App Integration Tests', () => {
  const mockBooks = [
    {
      id: 1,
      title: "Test Book 1",
      isbn: "123",
      authors: [{ name: "Author 1" }],
      price: 19.99,
      availableQuantity: 5,
    },
    {
      id: 2,
      title: "Test Book 2",
      isbn: "456",
      authors: [{ name: "Author 2" }],
      price: 24.99,
      availableQuantity: 3,
    },
  ];

  const mockToken = "mock-jwt-token";

  const setupAuthenticatedUser = () => {
    localStorage.setItem("jwtToken", mockToken);
  };

  beforeEach(() => {
    vi.clearAllMocks();
    localStorage.clear();

    api.fetchCart.mockResolvedValue({ cartItems: [], cart: {} });
    api.fetchBooks.mockResolvedValue(mockBooks);
    api.login.mockResolvedValue({ token: mockToken });
  });

  it("should render the app with header and book list", async () => {
    setupAuthenticatedUser();
    render(<App />);

    expect(screen.getByText("📚 Bookstore")).toBeInTheDocument();

    await waitFor(() => {
      expect(screen.getByText("Test Book 1")).toBeInTheDocument();
    });
  });

  it("should allow adding books to cart", async () => {
    setupAuthenticatedUser();

    api.addToCart.mockResolvedValue({
      cartItems: [
        {
          book: { id: 1, title: "Test Book 1", price: 19.99 },
          purchasedQuantity: 1,
        },
      ],
      cart: { id: 1 },
    });

    render(<App />);

    await waitFor(() => {
      expect(screen.getByText("Test Book 1")).toBeInTheDocument();
    });

    const addButtons = screen.getAllByRole("button", { name: /add to cart/i });
    fireEvent.click(addButtons[0]);

    await waitFor(() => {
      expect(api.addToCart).toHaveBeenCalledWith(mockToken, 1, 1);
    });
  });

  it("should navigate to cart view", async () => {
    setupAuthenticatedUser();
    render(<App />);

    await waitFor(() => {
      expect(screen.getByText("Test Book 1")).toBeInTheDocument();
    });

    const cartButton = screen.getByRole("button", { name: /^cart/i });
    fireEvent.click(cartButton);

    await waitFor(() => {
      expect(screen.getByText(/shopping cart/i)).toBeInTheDocument();
    });
  });

  it("should display cart item count in header", async () => {
    setupAuthenticatedUser();

    api.fetchCart.mockResolvedValue({
      cartItems: [
        {
          book: { id: 1, title: "Test Book 1", price: 19.99 },
          purchasedQuantity: 2,
        },
      ],
      cart: { id: 1 },
    });

    render(<App />);

    await waitFor(() => {
      expect(screen.getByText(/cart \(2\)/i)).toBeInTheDocument();
    });
  });
});
