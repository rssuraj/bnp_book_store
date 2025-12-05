import { useState, useEffect } from 'react';
import BookList from './components/BookList';
import ShoppingCart from './components/ShoppingCart';
import Checkout from './components/Checkout';
import OrderConfirmation from './components/OrderConfirmation';
import { addToCart, fetchCart, updateCartItem, createOrder, completeOrder, register, login } from './services/api';
import Login from './components/Login';
import Register from './components/Register';

const App = () => {
    const [cart, setCart] = useState({});
    const [order, setOrder] = useState({});
    const [cartItems, setCartItems] = useState([]);
    const [cartTotal, setCartTotal] = useState(0);
    const [currentView, setCurrentView] = useState('login');
    const [orderDetails, setOrderDetails] = useState(null);
    const [token, setToken] = useState(localStorage.getItem('jwtToken') || '');
  
    useEffect(() => {
      if(token) {
        setCurrentView('books');
        loadCart();
      }
    }, [token]);

    const handleRegister = async (userData) => {
      try {
        await register(userData);
        setCurrentView('login');
      } catch (error) {
        console.error('Error creating user:', error);
        alert('Error creating user');    
      }
    };

    const handleLogin = async (userData) => {
      try {
        const response = await login(userData);
        localStorage.setItem('jwtToken', response.token);
        setToken(response.token);
        setCurrentView('books');
      } catch (error) {
        console.error('Error logging user:', error);
        alert('Error logging user');    
      }
    }
  
    const loadCart = async () => {
      try {
        const cart = await fetchCart(token);
        setCartItems(cart.cartItems);
        setCart(cart.cart || {});
        setCartTotal(cart.cartItems.reduce((sum, item) => sum + (item.purchasedQuantity * item.book.price), 0));
      } catch (error) {
        console.error('Error loading cart:', error);
      }
    };
  
    const handleAddToCart = async (book) => {
      let cartItem = cartItems.find(ci => ci.book.id == book.id);
      if(cartItem || cartItems.length > 0) {
        if(!cartItem) cartItem = { purchasedQuantity: 0 };
        handleUpdateQuantity(book.id, cartItem.purchasedQuantity + 1);
      } else {
        try {
          const cart = await addToCart(token, book.id, 1);
          setCartItems(cart.cartItems);
          setCart(cart.cart || {});
          setCartTotal(cart.cartItems.reduce((sum, item) => sum + (item.purchasedQuantity * item.book.price), 0));
          alert(`${book.title} added to cart!`);
        } catch (error) {
          console.error('Error adding to cart:', error);
          alert('Failed to add item to cart');
        }
      }
    };
  
    const handleUpdateQuantity = async (bookId, quantity) => {
      try {
        const cart = await updateCartItem(token, bookId, quantity);
        setCartItems(cart.cartItems);
        setCart(cart.cart || {});
        setCartTotal(cart.cartItems.reduce((sum, item) => sum + (item.purchasedQuantity * item.book.price), 0));
      } catch (error) {
        console.error('Error updating quantity:', error);
        alert('Failed to update quantity');
      }
    };
  
    const handleRemoveItem = async (item) => {
      try {
        const cart = await updateCartItem(token, item.book.id, 0);
        setCartItems(cart.cartItems);
        setCart(cart.cart || {});
        setCartTotal(cart.cartItems.reduce((sum, item) => sum + (item.purchasedQuantity * item.book.price), 0));
      } catch (error) {
        console.error('Error removing item:', error);
        alert('Failed to remove item');
      }
    };
  
    const handleCheckout = async () => {
      try {
        // Create order
        const order = await createOrder(token, { cartId: cart.id, paymentComplete: false });
        setOrder(order)
        setCurrentView('checkout');
      } catch (error) {
        console.error('Error creating order:', error);
        alert('Error creating order');
      }
    };
  
    const handleCompleteOrder = async (paymentData) => {
      try {

        // Complete payment
        const result = await completeOrder(token, order.id, { cartId: cart.id, paymentComplete: true });
  
        // Set order details for confirmation
        setOrderDetails({
          orderId: result.id,
          items: cartItems,
          total: cartTotal,
        });
  
        // Clear cart and show confirmation
        setCartItems([]);
        setCartTotal(0);
        setCart({});
        setOrder({});
        setCurrentView('confirmation');
      } catch (error) {
        console.error('Error completing order:', error);
        throw error;
      }
    };
  
    const handleBackToStore = () => {
      setCurrentView('books');
      loadCart();
    };
  
    const cartItemCount = cartItems.reduce((sum, item) => sum + item.purchasedQuantity, 0);
  
    return (
      <div className="min-h-screen bg-gray-100">
        {/* Header */}
        <header className="bg-blue-600 text-white shadow-lg">
          <div className="container mx-auto px-4 py-4">
            <div className="flex items-center justify-between">
              <h1 className="text-3xl font-bold cursor-pointer" onClick={() => setCurrentView('books')}>
                📚 Bookstore
              </h1>
              <span className='flex justify-between'>
                {currentView === 'login' ? (
                  <button
                    onClick={() => setCurrentView('register')}
                    className="flex items-center gap-2 bg-white text-blue-600 px-4 py-2 rounded-md font-semibold hover:bg-gray-100 transition-colors mr-2"
                  >
                    <svg 
                      fill="none" 
                      viewBox="0 0 24 24" 
                      stroke-width="2" 
                      stroke="currentColor" 
                      class="w-6 h-6">
                      <path stroke-linecap="round" stroke-linejoin="round" d="M12 4v16m8-8H4" />
                    </svg>
                    Register
                  </button>
                ) : (
                  <button
                    onClick={() => setCurrentView('login')}
                    className="flex items-center gap-2 bg-white text-blue-600 px-4 py-2 rounded-md font-semibold hover:bg-gray-100 transition-colors mr-2"
                  >
                    <svg 
                      fill="none"
                      viewBox="0 0 24 24"
                      stroke-width="2"
                      stroke="currentColor"
                      class="w-6 h-6">
                      <path stroke-linecap="round" stroke-linejoin="round"
                          d="M8.25 9V5.25A2.25 2.25 0 0110.5 3h6A2.25 2.25 0 0118.75 5.25v13.5A2.25 2.25 0 0116.5 21h-6a2.25 2.25 0 01-2.25-2.25V15M11 12H20m-9 0l3-3m-3 3l3 3" />
                    </svg>
                    Login
                  </button>
                )}

                <button
                  onClick={() => setCurrentView('cart')}
                  className="flex items-center gap-2 bg-white text-blue-600 px-4 py-2 rounded-md font-semibold hover:bg-gray-100 transition-colors mr-2"
                >
                  <svg
                    className="w-6 h-6"
                    fill="none"
                    stroke="currentColor"
                    viewBox="0 0 24 24"
                  >
                    <path
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      strokeWidth={2}
                      d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z"
                    />
                  </svg>
                  Cart {cartItemCount > 0 && `(${cartItemCount})`}
                </button>
                <button
                  onClick={() => { 
                    setToken('');
                    setCurrentView('login');
                  }}
                  className="flex items-center gap-2 bg-white text-blue-600 px-4 py-2 rounded-md font-semibold hover:bg-gray-100 transition-colors"
                >
                  <svg xmlns="http://www.w3.org/2000/svg"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke-width="2"
                    stroke="currentColor"
                    class="w-6 h-6">
                  <path stroke-linecap="round" stroke-linejoin="round"
                        d="M15.75 9V5.25A2.25 2.25 0 0013.5 3h-6A2.25 2.25 0 005.25 5.25v13.5A2.25 2.25 0 007.5 21h6a2.25 2.25 0 002.25-2.25V15M18 12H9m9 0l-3 3m3-3l-3-3" />
                  </svg>
                  Logout
                </button>
              </span>
            </div>
          </div>
        </header>
  
        {/* Main Content */}
        <main className="container mx-auto px-4 py-8">
          {currentView === 'books' && (
            <BookList token={token} onAddToCart={handleAddToCart} />
          )}

          {currentView === 'login' && (
            <Login handleLogin={handleLogin} />
          )}

          {currentView === 'register' && (
            <Register handleRegister={handleRegister} />
          )}
  
          {currentView === 'cart' && (
            <div>
              <button
                onClick={() => setCurrentView('books')}
                className="mb-4 text-blue-600 hover:text-blue-800 font-semibold"
              >
                ← Back to Books
              </button>
              <ShoppingCart
                items={cartItems}
                total={cartTotal}
                onUpdateQuantity={handleUpdateQuantity}
                onRemoveItem={handleRemoveItem}
                onCheckout={handleCheckout}
              />
            </div>
          )}
  
          {currentView === 'checkout' && (
            <div>
              <button
                onClick={() => setCurrentView('cart')}
                className="mb-4 text-blue-600 hover:text-blue-800 font-semibold"
              >
                ← Back to Cart
              </button>
              <Checkout
                items={cartItems}
                total={cartTotal}
                onComplete={handleCompleteOrder}
                onCancel={() => setCurrentView('cart')}
              />
            </div>
          )}
  
          {currentView === 'confirmation' && orderDetails && (
            <OrderConfirmation
              orderId={orderDetails.id}
              items={orderDetails.items}
              total={orderDetails.total}
              onBackToStore={handleBackToStore}
            />
          )}
        </main>
      </div>
    );
}

export default App;
