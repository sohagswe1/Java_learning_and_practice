import { useAuth } from '../context/AuthContext';
import { Link, useNavigate } from 'react-router-dom';

export default function Navbar() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <nav className="dashboard-navbar">
      <div className="logo">
        ⚽ <span>Sports</span>Hub
      </div>
      
      <div className="nav-links">
        {user ? (
          <>
            {user.role === 'USER' && (
              <>
                <Link to="/dashboard" className="nav-item" style={{ marginRight: '1rem', color: 'white', textDecoration: 'none' }}>⚽ Products</Link>
                <Link to="/cart" className="nav-item" style={{ marginRight: '1.5rem', color: 'white', textDecoration: 'none' }}>🛒 Cart</Link>
              </>
            )}
            {user.role === 'ADMIN' && (
              <>
                <Link to="/admin" className="nav-item" style={{ marginRight: '1rem', color: 'white', textDecoration: 'none' }}>📊 Dashboard</Link>
                <Link to="/admin/add-product" className="nav-item" style={{ marginRight: '1rem', color: 'white', textDecoration: 'none' }}>➕ Add Product</Link>
                <Link to="/admin/orders" className="nav-item" style={{ marginRight: '1rem', color: 'white', textDecoration: 'none' }}>📦 Orders</Link>
                <Link to="/admin/users" className="nav-item" style={{ marginRight: '1.5rem', color: 'white', textDecoration: 'none' }}>👥 Users</Link>
              </>
            )}
            <span style={{ color: 'var(--text-muted)', marginRight: '1.5rem' }}>
              Welcome, <strong style={{ color: 'white' }}>{user.username}</strong>
            </span>
            <button onClick={handleLogout} className="btn btn-danger" style={{ padding: '0.4rem 1rem', width: 'auto' }}>
              Logout
            </button>
          </>
        ) : (
          <>
            <Link to="/login" className="nav-item">Login</Link>
            <Link to="/register" className="nav-item">Register</Link>
          </>
        )}
      </div>
    </nav>
  );
}
