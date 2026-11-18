import { useState, useEffect } from 'react';
import Navbar from '../components/Navbar';
import axios from 'axios';

export default function ManageOrders() {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  const fetchOrders = async () => {
    try {
      const res = await axios.get('/admin/orders');
      setOrders(res.data);
    } catch (err) {
      console.error('Error fetching orders', err);
      setError('Failed to fetch orders');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchOrders();
  }, []);

  const handleUpdateStatus = async (orderId, newStatus) => {
    setError('');
    setSuccess('');
    try {
      await axios.put(`/admin/orders/${orderId}/status?status=${newStatus}`);
      setSuccess(`Order status updated to ${newStatus}`);
      fetchOrders();
    } catch (err) {
      setError(err.response?.data?.error || 'Failed to update order status');
    }
  };

  const getStatusColor = (status) => {
    switch (status) {
      case 'PENDING': return '#f59e0b';
      case 'SHIPPED': return '#3b82f6';
      case 'DELIVERED': return '#10b981';
      case 'CANCELLED': return '#ef4444';
      default: return 'var(--text-muted)';
    }
  };

  return (
    <div className="page-transition">
      <Navbar />
      
      <div className="container">
        <h1 style={{ fontSize: '2.5rem', marginBottom: '1.5rem' }}>Manage Orders</h1>

        {success && <div className="alert alert-success">{success}</div>}
        {error && <div className="alert alert-error">{error}</div>}

        {loading ? (
          <div className="spinner"></div>
        ) : orders.length === 0 ? (
          <div className="glass-card" style={{ textAlign: 'center', padding: '3rem' }}>
            <span style={{ fontSize: '3rem' }}>📦</span>
            <h3 style={{ marginTop: '1rem', color: 'var(--text-muted)' }}>No orders placed yet.</h3>
          </div>
        ) : (
          <div className="glass-card" style={{ overflowX: 'auto', padding: '1rem' }}>
            <table style={{ width: '100%', borderCollapse: 'collapse', textAlign: 'left' }}>
              <thead>
                <tr style={{ borderBottom: '1px solid var(--border-color)', color: 'var(--text-muted)' }}>
                  <th style={{ padding: '1rem' }}>Order ID</th>
                  <th style={{ padding: '1rem' }}>Customer</th>
                  <th style={{ padding: '1rem' }}>Address</th>
                  <th style={{ padding: '1rem' }}>Items</th>
                  <th style={{ padding: '1rem' }}>Total Amount</th>
                  <th style={{ padding: '1rem' }}>Status</th>
                  <th style={{ padding: '1rem', textAlign: 'right' }}>Actions</th>
                </tr>
              </thead>
              <tbody>
                {orders.map((order) => (
                  <tr key={order.id} style={{ borderBottom: '1px solid var(--border-color)', transition: 'background 0.2s' }} className="table-row-hover">
                    <td style={{ padding: '1rem', fontWeight: 'bold' }}>#{order.id}</td>
                    <td style={{ padding: '1rem' }}>{order.user?.username}</td>
                    <td style={{ padding: '1rem', fontSize: '0.9rem', maxWidth: '200px', whiteSpace: 'nowrap', overflow: 'hidden', textOverflow: 'ellipsis' }}>
                      {order.shippingAddress}
                    </td>
                    <td style={{ padding: '1rem', fontSize: '0.9rem' }}>
                      {order.items?.map((item) => (
                        <div key={item.id} style={{ marginBottom: '0.2rem' }}>
                          • {item.product?.name} x{item.quantity} 
                          {item.unitPrice === 0 && <span style={{ color: 'var(--success)', marginLeft: '0.5rem', fontWeight: '500' }}>[FREE GIFT]</span>}
                        </div>
                      ))}
                    </td>
                    <td style={{ padding: '1rem', fontWeight: '600', color: 'var(--success)' }}>
                      ৳ {order.finalAmount}
                      <div style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>
                        Delivery: ৳{order.deliveryCharge}
                      </div>
                    </td>
                    <td style={{ padding: '1rem' }}>
                      <span style={{ 
                        background: `${getStatusColor(order.status)}20`, 
                        color: getStatusColor(order.status),
                        border: `1px solid ${getStatusColor(order.status)}40`,
                        padding: '0.25rem 0.6rem',
                        borderRadius: '20px',
                        fontSize: '0.85rem',
                        fontWeight: '600'
                      }}>
                        {order.status}
                      </span>
                    </td>
                    <td style={{ padding: '1rem', textAlign: 'right' }}>
                      <div style={{ display: 'flex', gap: '0.5rem', justifyContent: 'flex-end' }}>
                        {order.status === 'PENDING' && (
                          <>
                            <button 
                              onClick={() => handleUpdateStatus(order.id, 'SHIPPED')}
                              className="btn btn-primary" style={{ width: 'auto', padding: '0.4rem 0.8rem', fontSize: '0.85rem' }}
                            >
                              Ship
                            </button>
                            <button 
                              onClick={() => handleUpdateStatus(order.id, 'CANCELLED')}
                              className="btn btn-danger" style={{ width: 'auto', padding: '0.4rem 0.8rem', fontSize: '0.85rem' }}
                            >
                              Cancel
                            </button>
                          </>
                        )}
                        {order.status === 'SHIPPED' && (
                          <button 
                            onClick={() => handleUpdateStatus(order.id, 'DELIVERED')}
                            className="btn btn-primary" style={{ width: 'auto', padding: '0.4rem 0.8rem', fontSize: '0.85rem', background: 'linear-gradient(135deg, #10b981, #059669)' }}
                          >
                            Deliver
                          </button>
                        )}
                        {(order.status === 'DELIVERED' || order.status === 'CANCELLED') && (
                          <span style={{ color: 'var(--text-muted)', fontSize: '0.85rem' }}>No actions</span>
                        )}
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  );
}
