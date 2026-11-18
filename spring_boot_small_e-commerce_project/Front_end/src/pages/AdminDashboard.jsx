import Navbar from '../components/Navbar';
import { useAuth } from '../context/AuthContext';
import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

export default function AdminDashboard() {
  const { user } = useAuth();
  const [stats, setStats] = useState({ users: 0, products: 0, orders: 0 });
  
  useEffect(() => {
    const fetchStats = async () => {
      try {
        const [usersRes, productsRes, ordersRes] = await Promise.all([
          axios.get('/admin/users'),
          axios.get('/products'),
          axios.get('/admin/orders')
        ]);
        
        setStats({
          users: usersRes.data.length,
          products: productsRes.data.length,
          orders: ordersRes.data.length
        });
      } catch (err) {
        console.error("Error fetching admin stats", err);
      }
    };
    
    fetchStats();
  }, []);

  return (
    <div className="page-transition">
      <Navbar />
      
      <div className="container">
        <div className="dashboard-header">
          <div>
            <h1 style={{ fontSize: '2.5rem', marginBottom: '0.5rem' }}>Admin Control Panel</h1>
            <p style={{ color: 'var(--text-muted)' }}>Manage your SportsHub store.</p>
          </div>
        </div>

        <div className="stats-grid">
          <div className="glass-card stat-card" style={{ borderLeft: '4px solid var(--primary-color)' }}>
            <span className="stat-title">Total Users</span>
            <span className="stat-value">{stats.users}</span>
          </div>
          
          <div className="glass-card stat-card" style={{ borderLeft: '4px solid var(--success)' }}>
            <span className="stat-title">Total Products</span>
            <span className="stat-value">{stats.products}</span>
          </div>

          <div className="glass-card stat-card" style={{ borderLeft: '4px solid #f59e0b' }}>
            <span className="stat-title">Total Orders</span>
            <span className="stat-value">{stats.orders}</span>
          </div>
        </div>

        <div className="glass-card" style={{ marginTop: '2rem' }}>
          <h2 style={{ marginBottom: '1rem' }}>Quick Actions</h2>
          <div style={{ display: 'flex', gap: '1rem', flexWrap: 'wrap' }}>
            <Link to="/admin/products" className="btn btn-primary" style={{ width: 'auto', background: 'linear-gradient(135deg, #3b82f6, #2563eb)' }}>
              Manage Products
            </Link>
            <Link to="/admin/add-product" className="btn btn-primary" style={{ width: 'auto' }}>
              + Add Product
            </Link>
            <Link to="/admin/orders" className="btn btn-primary" style={{ width: 'auto', background: 'linear-gradient(135deg, #10b981, #059669)' }}>
              Manage Orders
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
}
