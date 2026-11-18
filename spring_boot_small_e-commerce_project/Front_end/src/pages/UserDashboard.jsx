import Navbar from '../components/Navbar';
import { useAuth } from '../context/AuthContext';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

export default function UserDashboard() {
  const { user } = useAuth();
  const [products, setProducts] = useState([]);
  const [cart, setCart] = useState(null);
  const [orders, setOrders] = useState([]);
  const [notification, setNotification] = useState('');
  const navigate = useNavigate();

  const fetchCart = async () => {
    try {
      const cartRes = await axios.get('/cart');
      setCart(cartRes.data);
    } catch (err) {
      console.error("Error fetching cart", err);
    }
  };

  const fetchProducts = async () => {
    try {
      const prodRes = await axios.get('/products');
      setProducts(prodRes.data);
    } catch (err) {
      console.error("Error fetching products", err);
    }
  };

  const fetchOrders = async () => {
    try {
      const orderRes = await axios.get('/orders/my');
      setOrders(orderRes.data);
    } catch (err) {
      console.error("Error fetching orders", err);
    }
  };

  useEffect(() => {
    fetchProducts();
    fetchCart();
    fetchOrders();
  }, []);

  const handleAddToCart = async (productId) => {
    try {
      setNotification('');
      const res = await axios.post(`/cart/add?productId=${productId}&quantity=1`);
      setCart(res.data);
      setNotification('Product added to cart successfully!');
      setTimeout(() => setNotification(''), 3000);
    } catch (err) {
      console.error("Error adding to cart", err);
      alert("Failed to add product to cart");
    }
  };

  return (
    <div className="page-transition">
      <Navbar />
      
      <div className="container">
        {notification && <div className="alert alert-success">{notification}</div>}

        <div className="dashboard-header">
          <div>
            <h1 style={{ fontSize: '2.5rem', marginBottom: '0.5rem' }}>Welcome to SportsHub!</h1>
            <p style={{ color: 'var(--text-muted)' }}>Find the best gear for your game.</p>
          </div>
        </div>

        <div className="stats-grid">
          <div className="glass-card stat-card" style={{ cursor: 'pointer' }} onClick={() => navigate('/cart')}>
            <span className="stat-title">My Cart Items</span>
            <span className="stat-value" style={{ color: 'var(--primary-color)' }}>
              {cart ? cart.items?.length || 0 : 0}
            </span>
          </div>
          
          <div className="glass-card stat-card">
            <span className="stat-title">My Orders</span>
            <span className="stat-value" style={{ color: 'var(--success)' }}>
              {orders.length}
            </span>
          </div>
        </div>

        {orders.length > 0 && (
          <>
            <h2 style={{ marginTop: '2rem', marginBottom: '1.5rem', borderBottom: '1px solid var(--border-color)', paddingBottom: '0.5rem' }}>
              My Recent Orders
            </h2>
            <div className="glass-card" style={{ overflowX: 'auto', marginBottom: '2rem' }}>
              <table style={{ width: '100%', borderCollapse: 'collapse', textAlign: 'left' }}>
                <thead>
                  <tr style={{ borderBottom: '2px solid var(--border-color)', color: 'var(--text-muted)' }}>
                    <th style={{ padding: '1rem' }}>Order ID</th>
                    <th style={{ padding: '1rem' }}>Date</th>
                    <th style={{ padding: '1rem' }}>Total Amount</th>
                    <th style={{ padding: '1rem' }}>Status</th>
                  </tr>
                </thead>
                <tbody>
                  {orders.slice(0, 5).map(o => (
                    <tr key={o.id} style={{ borderBottom: '1px solid var(--border-color)' }}>
                      <td style={{ padding: '1rem' }}>#{o.id}</td>
                      <td style={{ padding: '1rem' }}>{new Date(o.createdAt).toLocaleDateString()}</td>
                      <td style={{ padding: '1rem', fontWeight: '600' }}>৳ {o.totalAmount}</td>
                      <td style={{ padding: '1rem' }}>
                        <span style={{
                          background: o.status === 'PENDING' ? 'rgba(245, 158, 11, 0.15)' : o.status === 'DELIVERED' ? 'rgba(16, 185, 129, 0.15)' : 'rgba(239, 68, 68, 0.15)',
                          color: o.status === 'PENDING' ? '#fbbf24' : o.status === 'DELIVERED' ? '#34d399' : '#f87171',
                          padding: '0.2rem 0.5rem',
                          borderRadius: '4px',
                          fontSize: '0.8rem',
                          fontWeight: '600'
                        }}>
                          {o.status}
                        </span>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </>
        )}

        <h2 style={{ marginBottom: '1.5rem', borderBottom: '1px solid var(--border-color)', paddingBottom: '0.5rem' }}>
          All Products
        </h2>
        
        <div className="stats-grid">
          {products.map(p => (
            <div key={p.id} className="glass-card" style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
              <div style={{ height: '180px', background: 'rgba(0,0,0,0.2)', borderRadius: '8px', display: 'flex', alignItems: 'center', justifyContent: 'center', overflow: 'hidden' }}>
                {p.imageUrl ? (
                  <img 
                    src={`http://localhost:8080${p.imageUrl}`} 
                    alt={p.name} 
                    style={{ width: '100%', height: '100%', objectFit: 'cover' }} 
                  />
                ) : (
                  <span style={{ fontSize: '3rem' }}>🛍️</span>
                )}
              </div>
              <div style={{ display: 'flex', flexDirection: 'column', flexGrow: 1, justifyContent: 'space-between' }}>
                <div>
                  <h3 style={{ fontSize: '1.2rem', marginBottom: '0.25rem' }}>{p.name}</h3>
                  <p style={{ color: 'var(--text-muted)', fontSize: '0.9rem', marginBottom: '0.5rem' }}>Brand: {p.brand}</p>
                  <p style={{ fontSize: '0.85rem', color: 'var(--text-muted)', marginBottom: '1rem', height: '40px', overflow: 'hidden', textOverflow: 'ellipsis' }}>
                    {p.description}
                  </p>
                  {p.hasFreeGift && (
                    <div style={{ background: 'rgba(16, 185, 129, 0.1)', border: '1px solid rgba(16, 185, 129, 0.2)', color: '#6ee7b7', padding: '0.3rem 0.6rem', borderRadius: '6px', fontSize: '0.8rem', display: 'inline-block', marginBottom: '1rem', fontWeight: '500' }}>
                      🎁 Free {p.freeGiftDescription}!
                    </div>
                  )}
                </div>
                
                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                  <span style={{ fontSize: '1.25rem', fontWeight: '700', color: 'var(--success)' }}>৳ {p.price}</span>
                  <button 
                    onClick={() => handleAddToCart(p.id)}
                    className="btn btn-primary" 
                    style={{ width: 'auto', padding: '0.5rem 1rem', fontSize: '0.9rem' }}
                  >
                    Add to Cart
                  </button>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
