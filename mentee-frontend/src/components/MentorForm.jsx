import React, { useState } from 'react';
import axios from 'axios';

function MentorForm() {
  const [mentor, setMentor] = useState({ name: '', email: '', password: '', expertise: '' });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post('http://localhost:8080/api/mentors', mentor);
      alert('Mentor added successfully');
    } catch (err) {
      console.error('Error adding mentor:', err);
      alert('Error adding mentor');
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Add Mentor</h2>
      <input type="text" placeholder="Name" onChange={e => setMentor({ ...mentor, name: e.target.value })} required />
      <input type="email" placeholder="Email" onChange={e => setMentor({ ...mentor, email: e.target.value })} required />
      <input type="password" placeholder="Password" onChange={e => setMentor({ ...mentor, password: e.target.value })} required />
      <input type="text" placeholder="Expertise" onChange={e => setMentor({ ...mentor, expertise: e.target.value })} required />
      <button type="submit">Add Mentor</button>
    </form>
  );
}

export default MentorForm;
