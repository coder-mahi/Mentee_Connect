import React, { useState } from 'react';
import axios from 'axios';

function AdminForm() {
  const [admin, setAdmin] = useState({ name: '', email: '', password: '' });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post('http://localhost:8080/api/admin', admin);
      alert('Admin added successfully');
    } catch (err) {
      console.error('Error adding admin:', err);
      alert('Error adding admin');
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Add Admin</h2>
      <input type="text" placeholder="Name" onChange={e => setAdmin({ ...admin, name: e.target.value })} required />
      <input type="email" placeholder="Email" onChange={e => setAdmin({ ...admin, email: e.target.value })} required />
      <input type="password" placeholder="Password" onChange={e => setAdmin({ ...admin, password: e.target.value })} required />
      <button type="submit">Add Admin</button>
    </form>
  );
}

export default AdminForm;
