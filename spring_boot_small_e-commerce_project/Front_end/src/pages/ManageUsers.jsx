import { useState, useEffect } from 'react';
import Navbar from '../components/Navbar';
import axios from 'axios';

export default function ManageUsers() {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  const fetchUsers = async () => {
    try {
      const res = await axios.get('/admin/users');
      // Only show non-admin users
      setUsers(res.data.filter(u => u.role !== 'ADMIN'));
    } catch (err) {
      console.error(err);
      setError('Failed to fetch users');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchUsers();
  }, []);

  const handleUpdateStars = async (userId, stars) => {
    setError('');
    setSuccess('');
    try {
      await axios.put(`/admin/users/${userId}/stars?stars=${stars}`);
      setSuccess('User stars updated successfully!');
      // Update local state
      setUsers(prev => prev.map(u => u.id === userId ? { ...u, stars } : u));
      setTimeout(() => setSuccess(''), 3000);
    } catch (err) {
      console.error(err);
      setError('Failed to update user stars');
    }
  };

  return (
    <div className="page-transition">
      <Navbar />

      <div className="container">
        <div className="dashboard-header">
          <div>
            <h1 style={{ fontSize: '2.5rem', marginBottom: '0.5rem' }}>Manage Users</h1>
            <p style={{ color: 'var(--text-muted)' }}>View registered users and assign star ratings for order discounts.</p>
          </div>
        </div>

        {success && <div className="alert alert-success">{success}</div>}
        {error && <div className="alert alert-error">{error}</div>}

        {loading ? (
          <div className="spinner"></div>
        ) : (
          <div className="glass-card" style={{ overflowX: 'auto', padding: '1.5rem' }}>
            <table style={{ width: '100%', borderCollapse: 'collapse', textAlign: 'left' }}>
              <thead>
                <tr style={{ borderBottom: '2px solid var(--border-color)', color: 'var(--text-muted)' }}>
                  <th style={{ padding: '1rem' }}>ID</th>
                  <th style={{ padding: '1rem' }}>Username</th>
                  <th style={{ padding: '1rem' }}>Email</th>
                  <th style={{ padding: '1rem' }}>Role</th>
                  <th style={{ padding: '1rem' }}>Address</th>
                  <th style={{ padding: '1rem' }}>Stars (Discount %)</th>
                  <th style={{ padding: '1rem' }}>Actions</th>
                </tr>
              </thead>
              <tbody>
                {users.map((u) => (
                  <tr key={u.id} style={{ borderBottom: '1px solid var(--border-color)' }}>
                    <td style={{ padding: '1rem' }}>{u.id}</td>
                    <td style={{ padding: '1rem', fontWeight: '600' }}>{u.username}</td>
                    <td style={{ padding: '1rem' }}>{u.email}</td>
                    <td style={{ padding: '1rem' }}>
                      <span style={{
                        background: u.role === 'ADMIN' ? 'rgba(239, 68, 68, 0.15)' : 'rgba(59, 130, 246, 0.15)',
                        color: u.role === 'ADMIN' ? '#f87171' : '#60a5fa',
                        padding: '0.2rem 0.5rem',
                        borderRadius: '4px',
                        fontSize: '0.8rem',
                        fontWeight: '600'
                      }}>
                        {u.role}
                      </span>
                    </td>
                    <td style={{ padding: '1rem', color: 'var(--text-muted)' }}>{u.address || 'Not set'}</td>
                    <td style={{ padding: '1rem' }}>
                      <span style={{ color: '#fbbf24', fontSize: '1.1rem', marginRight: '0.25rem' }}>
                        {'★'.repeat(u.stars || 0)}{'☆'.repeat(5 - (u.stars || 0))}
                      </span>
                      <span style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>
                        ({(u.stars || 0) * 2}% Discount)
                      </span>
                    </td>
                    <td style={{ padding: '1rem' }}>
                      {u.role !== 'ADMIN' ? (
                        <select
                          value={u.stars || 0}
                          onChange={(e) => handleUpdateStars(u.id, parseInt(e.target.value))}
                          style={{
                            background: 'rgba(255, 255, 255, 0.05)',
                            border: '1px solid var(--border-color)',
                            borderRadius: '4px',
                            color: 'white',
                            padding: '0.3rem 0.5rem',
                            cursor: 'pointer'
                          }}
                        >
                          <option value="0" style={{ background: '#111827' }}>0 Stars (0%)</option>
                          <option value="1" style={{ background: '#111827' }}>1 Star (2%)</option>
                          <option value="2" style={{ background: '#111827' }}>2 Stars (4%)</option>
                          <option value="3" style={{ background: '#111827' }}>3 Stars (6%)</option>
                          <option value="4" style={{ background: '#111827' }}>4 Stars (8%)</option>
                          <option value="5" style={{ background: '#111827' }}>5 Stars (10%)</option>
                        </select>
                      ) : (
                        <span style={{ color: 'var(--text-muted)', fontSize: '0.85rem' }}>N/A</span>
                      )}
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
