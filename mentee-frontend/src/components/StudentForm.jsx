import React, { useState } from 'react';
import axios from 'axios';

function StudentForm() {
  const [student, setStudent] = useState({ name: '', email: '', password: '', mentorName: '' });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post('http://localhost:8080/api/students', student);
      alert('Student added successfully');
    } catch {
      alert('Error adding student');
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Add Student</h2>
      <input type="text" placeholder="Name" onChange={e => setStudent({ ...student, name: e.target.value })} required />
      <input type="email" placeholder="Email" onChange={e => setStudent({ ...student, email: e.target.value })} required />
      <input type="password" placeholder="Password" onChange={e => setStudent({ ...student, password: e.target.value })} required />
      <input type="text" placeholder="Mentor Name" onChange={e => setStudent({ ...student, mentorName: e.target.value })} required />
      <button type="submit">Add Student</button>
    </form>
  );
}

export default StudentForm;