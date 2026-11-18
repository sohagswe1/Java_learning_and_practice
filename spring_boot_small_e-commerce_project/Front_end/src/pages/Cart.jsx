import { useState, useEffect } from 'react';
import Navbar from '../components/Navbar';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

export default function Cart() {
  const [cart, setCart] = useState(null);
  const [currentUser, setCurrentUser] = useState(null);
  const [shippingAddress, setShippingAddress] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  
  const navigate = useNavigate();

  const fetchCart = async () => {
    try {
      const res = await axios.get('/cart');
      setCart(res.data);
      
      const userRes = await axios.get('/user/profile');
      setCurrentUser(userRes.data);
      setShippingAddress(userRes.data.address || '');
    } catch (err) {
      console.error('Error fetching cart', err);
      setError('Failed to load cart');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchCart();
  }, []);

  const handleUpdateQuantity = async (itemId, newQuantity) => {
    try {
      setError('');
      const res = await axios.put(`/cart/update/${itemId}?quantity=${newQuantity}`);
      setCart(res.data);
    } catch (err) {
      setError('Failed to update quantity');
    }
  };

  const handleRemoveItem = async (itemId) => {
    try {
      setError('');
      const res = await axios.delete(`/cart/remove/${itemId}`);
      setCart(res.data);
    } catch (err) {
      setError('Failed to remove item');
    }
  };

  const handlePlaceOrder = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');
    setLoading(true);

    if (!shippingAddress.trim()) {
      setError('Please provide a shipping address.');
      setLoading(false);
      return;
    }

    try {
      await axios.post(`/orders/place?shippingAddress=${encodeURIComponent(shippingAddress)}`);
      setSuccess('Order placed successfully! Redirecting...');
      setCart(null);
      setTimeout(() => {
        navigate('/dashboard'); // Go back to dashboard to see updated orders
      }, 2000);
    } catch (err) {
      setError(err.response?.data?.error || 'Failed to place order');
    } finally {
      setLoading(false);
    }
  };

  // Calculations
  const calculateSubtotal = () => {
    if (!cart || !cart.items) return 0;
    return cart.items.reduce((sum, item) => {
      return sum + ((item.free || item.isFree) ? 0 : (item.product?.price || 0) * item.quantity);
    }, 0);
  };

  const subtotal = calculateSubtotal();
  const stars = currentUser?.stars || 0;
  const discountPercentage = stars * 0.02;
  const discountAmount = Math.round(subtotal * discountPercentage);
  const discountedSubtotal = subtotal - discountAmount;
  const deliveryCharge = discountedSubtotal >= 2000 ? 0 : 100;
  const total = discountedSubtotal > 0 ? discountedSubtotal + deliveryCharge : 0;

  return (
    <div className="page-transition">
      <Navbar />
      
      <div className="container">
        <h1 style={{ fontSize: '2.5rem', marginBottom: '1.5rem' }}>Shopping Cart</h1>

        {success && <div className="alert alert-success">{success}</div>}
        {error && <div className="alert alert-error">{error}</div>}

        {loading ? (
          <div className="spinner"></div>
        ) : !cart || !cart.items || cart.items.length === 0 ? (
          <div className="glass-card" style={{ textAlign: 'center', padding: '3rem' }}>
            <span style={{ fontSize: '3.5rem' }}>🛒</span>
            <h3 style={{ marginTop: '1rem', color: 'var(--text-muted)' }}>Your cart is empty.</h3>
            <button className="btn btn-primary" style={{ width: 'auto', marginTop: '1.5rem' }} onClick={() => navigate('/dashboard')}>
              Go Shopping
            </button>
          </div>
        ) : (
          <div style={{ display: 'grid', gridTemplateColumns: '2fr 1fr', gap: '2rem', alignItems: 'start' }}>
            
            {/* Left side: Cart Items */}
            <div className="glass-card" style={{ display: 'flex', flexDirection: 'column', gap: '1.5rem' }}>
              {cart.items.map((item) => (
                <div key={item.id} style={{ display: 'flex', gap: '1rem', borderBottom: '1px solid var(--border-color)', paddingBottom: '1.5rem', alignItems: 'center' }}>
                  <div style={{ width: '80px', height: '80px', background: 'rgba(0,0,0,0.2)', borderRadius: '8px', display: 'flex', alignItems: 'center', justifyContent: 'center', overflow: 'hidden' }}>
                    {item.product?.imageUrl ? (
                      <img src={`http://localhost:8080${item.product.imageUrl}`} alt={item.product?.name || 'Product'} style={{ width: '100%', height: '100%', objectFit: 'cover' }} />
                    ) : (
                      <span style={{ fontSize: '2rem' }}>🛍️</span>
                    )}
                  </div>

                  <div style={{ flexGrow: 1 }}>
                    <h3 style={{ fontSize: '1.1rem' }}>{item.product?.name || 'Unknown Product'}</h3>
                    <p style={{ color: 'var(--text-muted)', fontSize: '0.85rem' }}>Brand: {item.product?.brand || 'Unknown'}</p>
                    {(item.free || item.isFree) ? (
                      <span style={{ background: 'rgba(16, 185, 129, 0.1)', color: '#6ee7b7', padding: '0.2rem 0.5rem', borderRadius: '4px', fontSize: '0.75rem', fontWeight: '500', display: 'inline-block', marginTop: '0.25rem' }}>
                        🎁 FREE GIFT
                      </span>
                    ) : (
                      <span style={{ fontWeight: '600', color: 'var(--success)', display: 'block', marginTop: '0.25rem' }}>
                        ৳ {item.product?.price || 0}
                      </span>
                    )}
                  </div>

                  {/* Quantity Actions */}
                  {!(item.free || item.isFree) && (
                    <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                      <button 
                        onClick={() => handleUpdateQuantity(item.id, item.quantity - 1)}
                        className="btn" style={{ width: '32px', height: '32px', padding: 0, background: 'rgba(255,255,255,0.05)', border: '1px solid var(--border-color)', borderRadius: '50%', color: 'white' }}
                      >
                        -
                      </button>
                      <span style={{ width: '24px', textAlign: 'center', fontWeight: '600' }}>{item.quantity}</span>
                      <button 
                        onClick={() => handleUpdateQuantity(item.id, item.quantity + 1)}
                        className="btn" style={{ width: '32px', height: '32px', padding: 0, background: 'rgba(255,255,255,0.05)', border: '1px solid var(--border-color)', borderRadius: '50%', color: 'white' }}
                      >
                        +
                      </button>
                    </div>
                  )}

                  {(item.free || item.isFree) && (
                    <span style={{ width: '80px', textAlign: 'center', color: 'var(--text-muted)', fontSize: '0.9rem' }}>
                      Qty: {item.quantity}
                    </span>
                  )}

                  <div>
                    <button 
                      onClick={() => handleRemoveItem(item.id)}
                      className="btn btn-danger" style={{ width: 'auto', padding: '0.4rem 0.8rem', fontSize: '0.85rem' }}
                      disabled={item.free || item.isFree} // Free gifts are automatically managed
                      title={(item.free || item.isFree) ? "Free gift is automatically managed with the main product" : ""}
                    >
                      Delete
                    </button>
                  </div>
                </div>
              ))}
            </div>

            {/* Right side: Order Summary */}
            <div className="glass-card" style={{ display: 'flex', flexDirection: 'column', gap: '1.5rem' }}>
              <h2 style={{ fontSize: '1.5rem', borderBottom: '1px solid var(--border-color)', paddingBottom: '0.5rem' }}>Summary</h2>
              
              <div style={{ display: 'flex', justifyContent: 'space-between', color: 'var(--text-muted)' }}>
                <span>Subtotal:</span>
                <span style={{ color: 'white', fontWeight: '600' }}>৳ {subtotal}</span>
              </div>

              {stars > 0 && (
                <div style={{ display: 'flex', justifyContent: 'space-between', color: '#fbbf24' }}>
                  <span>Loyalty Discount ({'★'.repeat(stars)} {stars * 2}%):</span>
                  <span style={{ fontWeight: '600' }}>- ৳ {discountAmount}</span>
                </div>
              )}

              <div style={{ display: 'flex', justifyContent: 'space-between', color: 'var(--text-muted)' }}>
                <span>Delivery:</span>
                {deliveryCharge === 0 ? (
                  <span style={{ color: 'var(--success)', fontWeight: '600' }}>FREE</span>
                ) : (
                  <span style={{ color: 'white', fontWeight: '600' }}>৳ {deliveryCharge}</span>
                )}
              </div>
              
              <div style={{ display: 'flex', justifyContent: 'space-between', fontSize: '1.25rem', fontWeight: '700', borderTop: '1px solid var(--border-color)', paddingTop: '1rem' }}>
                <span>Total:</span>
                <span style={{ color: 'var(--success)' }}>৳ {total}</span>
              </div>

              <form onSubmit={handlePlaceOrder} style={{ display: 'flex', flexDirection: 'column', gap: '1rem', marginTop: '1rem' }}>
                <div className="form-group" style={{ marginBottom: 0 }}>
                  <label>Shipping Address</label>
                  <textarea 
                    className="form-input" style={{ minHeight: '80px' }}
                    value={shippingAddress} onChange={(e) => setShippingAddress(e.target.value)} required
                    placeholder="Enter full address for delivery"
                  />
                </div>

                <button type="submit" className="btn btn-primary" style={{ background: 'linear-gradient(135deg, #10b981, #059669)' }} disabled={loading}>
                  {loading ? 'Processing...' : 'Place Order'}
                </button>
              </form>
            </div>

          </div>
        )}
      </div>
    </div>
  );
}
