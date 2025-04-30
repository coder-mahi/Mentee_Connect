import React, { useState } from 'react';
import axios from 'axios';

function BatchForm() {
  const [batch, setBatch] = useState({ batchName: '', course: '', mentorAssigned: '' });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post('http://localhost:8080/api/batches', batch);
      alert('Batch created successfully');
    } catch (err) {
      console.error('Error creating batch:', err);
      alert('Error creating batch');
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Create Batch</h2>
      <input type="text" placeholder="Batch Name" onChange={e => setBatch({ ...batch, batchName: e.target.value })} required />
      <input type="text" placeholder="Course" onChange={e => setBatch({ ...batch, course: e.target.value })} required />
      <input type="text" placeholder="Mentor Assigned" onChange={e => setBatch({ ...batch, mentorAssigned: e.target.value })} required />
      <button type="submit">Create Batch</button>
    </form>
  );
}

export default BatchForm;