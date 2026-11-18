import { useState } from 'react';
import Navbar from '../components/Navbar';
import axios from 'axios';

export default function AddProduct() {
  const [formData, setFormData] = useState({
    name: 'Football', // default option
    description: '',
    price: '',
    stock: '',
    brand: '',
    hasFreeGift: false,
    freeGiftDescription: 'Anklet'
  });
  const [imageFile, setImageFile] = useState(null);
  
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [loading, setLoading] = useState(false);

  const productNames = [
    'Football',
    'Cricket Bat',
    'Badminton Racket',
    'Sticker',
    'Grip',
    'Anklet'
  ];

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData({
      ...formData,
      [name]: type === 'checkbox' ? checked : value
    });
  };

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      const fileSizeMB = file.size / (1024 * 1024);
      if (fileSizeMB > 10) {
        setError('Image size must be less than 10 MB');
        setImageFile(null);
        e.target.value = '';
      } else {
        setError('');
        setImageFile(file);
      }
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');
    setLoading(true);

    try {
      const data = new FormData();
      data.append('name', formData.name);
      data.append('description', formData.description);
      data.append('price', formData.price);
      data.append('stock', formData.stock);
      data.append('brand', formData.brand);
      data.append('hasFreeGift', formData.hasFreeGift);
      data.append('freeGiftDescription', formData.freeGiftDescription);
      
      if (imageFile) {
        data.append('image', imageFile);
      }

      // No categoryId needed now, backend will automatically handle category mapping
      await axios.post('/admin/products', data, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      });

      setSuccess('Product added successfully!');
      // Reset form
      setFormData({
        name: 'Football',
        description: '',
        price: '',
        stock: '',
        brand: '',
        hasFreeGift: false,
        freeGiftDescription: 'Anklet'
      });
      setImageFile(null);
      document.getElementById('imageInput').value = '';
    } catch (err) {
      setError(err.response?.data?.error || 'Failed to add product');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="page-transition">
      <Navbar />
      
      <div className="container" style={{ display: 'flex', justifyContent: 'center', padding: '2rem 1rem' }}>
        
        <div className="glass-card" style={{ width: '100%', maxWidth: '600px' }}>
          <h2 className="auth-title" style={{ marginBottom: '1.5rem', textAlign: 'left' }}>Add New Product</h2>
          
          {success && <div className="alert alert-success">{success}</div>}
          {error && <div className="alert alert-error">{error}</div>}

          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label>Select Product Type (Name)</label>
              <select 
                name="name" 
                className="form-input" 
                value={formData.name} 
                onChange={handleChange}
                required
              >
                {productNames.map(name => (
                  <option key={name} value={name} style={{ background: 'var(--bg-color)' }}>
                    {name}
                  </option>
                ))}
              </select>
            </div>

            <div className="form-group" style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '1rem' }}>
              <div>
                <label>Price (BDT)</label>
                <input 
                  type="number" step="0.01" name="price" className="form-input" 
                  value={formData.price} onChange={handleChange} required 
                />
              </div>
              <div>
                <label>Stock</label>
                <input 
                  type="number" name="stock" className="form-input" 
                  value={formData.stock} onChange={handleChange} required 
                />
              </div>
            </div>

            <div className="form-group">
              <label>Brand</label>
              <input 
                type="text" name="brand" className="form-input" 
                value={formData.brand} onChange={handleChange} required 
              />
            </div>

            <div className="form-group">
              <label>Product Image</label>
              <input 
                type="file" id="imageInput" accept="image/*" className="form-input" 
                onChange={handleImageChange} 
              />
            </div>

            <div className="form-group">
              <label>Description</label>
              <textarea 
                name="description" className="form-input" style={{ minHeight: '80px' }}
                value={formData.description} onChange={handleChange} required 
              ></textarea>
            </div>

            <div className="form-group" style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', marginBottom: '1.5rem' }}>
              <input 
                type="checkbox" name="hasFreeGift" id="hasFreeGift" 
                checked={formData.hasFreeGift} onChange={handleChange}
                style={{ width: '18px', height: '18px', cursor: 'pointer' }}
              />
              <label htmlFor="hasFreeGift" style={{ margin: 0, cursor: 'pointer' }}>Includes Free Gift</label>
            </div>

            {formData.hasFreeGift && (
              <div className="form-group page-transition">
                <label>Select Free Gift Item</label>
                <select 
                  name="freeGiftDescription" 
                  className="form-input" 
                  value={formData.freeGiftDescription} 
                  onChange={handleChange}
                  required
                >
                  <option value="Anklet" style={{ background: 'var(--bg-color)' }}>Anklet</option>
                  <option value="Grip" style={{ background: 'var(--bg-color)' }}>Grip</option>
                  <option value="Sticker" style={{ background: 'var(--bg-color)' }}>Sticker</option>
                </select>
              </div>
            )}

            <button type="submit" className="btn btn-primary" disabled={loading}>
              {loading ? 'Adding Product...' : 'Add Product'}
            </button>
          </form>
        </div>

      </div>
    </div>
  );
}
