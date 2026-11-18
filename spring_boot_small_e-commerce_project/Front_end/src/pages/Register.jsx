import { useState } from 'react';
import { useAuth } from '../context/AuthContext';
import { useNavigate, Link } from 'react-router-dom';

export default function Register() {
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
    phone: '',
    address: ''
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  
  const { register, user } = useAuth();
  const navigate = useNavigate();

  if (user) {
    navigate(user.role === 'ADMIN' ? '/admin' : '/dashboard');
  }

  const handleChange = (e) => {
    setFormData({...formData, [e.target.name]: e.target.value});
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);
    
    const result = await register(formData);
    
    if (!result.success) {
      setError(result.error);
    }
    setLoading(false);
  };

  return (
    <div className="auth-container page-transition">
      <div className="glass-card auth-form">
        <h2 className="auth-title">Create Account</h2>
        
        {error && <div className="alert alert-error">{error}</div>}
        
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Username</label>
            <input 
              type="text" name="username" className="form-input"
              value={formData.username} onChange={handleChange} required 
            />
          </div>
          
          <div className="form-group">
            <label>Email</label>
            <input 
              type="email" name="email" className="form-input"
              value={formData.email} onChange={handleChange} required 
            />
          </div>

          <div className="form-group">
            <label>Password</label>
            <input 
              type="password" name="password" className="form-input"
              value={formData.password} onChange={handleChange} required 
            />
          </div>

          <div className="form-group">
            <label>Phone</label>
            <input 
              type="text" name="phone" className="form-input"
              value={formData.phone} onChange={handleChange} 
            />
          </div>

          <div className="form-group">
            <label>Address</label>
            <textarea 
              name="address" className="form-input" style={{ minHeight: '80px' }}
              value={formData.address} onChange={handleChange} 
            ></textarea>
          </div>
          
          <button type="submit" className="btn btn-primary" disabled={loading}>
            {loading ? 'Registering...' : 'Register'}
          </button>
        </form>
        
        <p style={{ marginTop: '1.5rem', textAlign: 'center', color: 'var(--text-muted)' }}>
          Already have an account? <Link to="/login">Login here</Link>
        </p>
      </div>
    </div>
  );
}
