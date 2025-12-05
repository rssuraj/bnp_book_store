const API_BASE_URL = 'http://localhost:8000/api';

export const register = async (userData) => {
  try {
    
    const response = await fetch(`${API_BASE_URL}/users/register`, {
      method: 'POST', 
      headers: { 
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(userData)
    });
    if (!response.ok) throw new Error('Failed to register the user');
    return await response.json();

  } catch (error) {
    console.error('Failed to register the user:', error);
    throw error;
  }
};

export const login = async (userData) => {
  try {
    
    const response = await fetch(`${API_BASE_URL}/users/login`, {
      method: 'POST', 
      headers: { 
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(userData)
    });
    if (!response.ok) throw new Error('Failed to login the user');
    return await response.json();

  } catch (error) {
    console.error('Failed to logib the user:', error);
    throw error;
  }
};

export const fetchBooks = async (token) => {
  try {
    
    const response = await fetch(`${API_BASE_URL}/books`, {
      method: 'GET', 
      headers: { 'Authorization': `Bearer ${token}` }
    });
    if (!response.ok) throw new Error('Failed to fetch books');
    return await response.json();

  } catch (error) {
    console.error('Error fetching books:', error);
    throw error;
  }
};

export const fetchCart = async (token) => {
  try {
    
    const response = await fetch(`${API_BASE_URL}/carts`, {
      method: 'GET', 
      headers: { 'Authorization': `Bearer ${token}` }
    });
    if (!response.ok) return { cartItems: [] };
    return await response.json();

  } catch (error) {
    console.error('Error fetching cart:', error);
    throw error;
  }
};

export const addToCart = async (token, bookId, quantity = 1) => {
  try {

    const response = await fetch(`${API_BASE_URL}/carts`, {
      method: 'POST',
      headers: { 
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
      body: JSON.stringify({ bookId, purchaseQuantity: quantity })
    });
    if (!response.ok) throw new Error('Failed to add to cart');
    return await response.json();

  } catch (error) {
    console.error('Error adding to cart:', error);
    throw error;
  }
};

export const updateCartItem = async (token, bookId, quantity) => {
  try {
    
    const response = await fetch(`${API_BASE_URL}/carts`, {
      method: 'PUT',
      headers: { 
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
      body: JSON.stringify({ bookId, purchaseQuantity: quantity })
    });
    if (!response.ok) throw new Error('Failed to update cart');
    return await response.json();

  } catch (error) {
    console.error('Error updating cart:', error);
    throw error;
  }
};

export const createOrder = async (token, orderData) => {
  try {
    
    const response = await fetch(`${API_BASE_URL}/orders`, {
      method: 'POST',
      headers: { 
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
      body: JSON.stringify(orderData)
    });
    if (!response.ok) throw new Error('Failed to create order');
    return await response.json();
    
  } catch (error) {
    console.error('Error creating order:', error);
    throw error;
  }
};

export const completeOrder = async (token, orderId, orderData) => {
  try {
    
    const response = await fetch(`${API_BASE_URL}/orders/${orderId}`, {
      method: 'PUT',
      headers: { 
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
      body: JSON.stringify(orderData)
    });
    if (!response.ok) throw new Error('Failed to complete order');
    return await response.json();
    
  } catch (error) {
    console.error('Error completing order:', error);
    throw error;
  }
};
