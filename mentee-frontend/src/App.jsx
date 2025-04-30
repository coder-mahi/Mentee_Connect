import React from 'react';
import LoginForm from './components/LoginForm';
import MentorForm from './components/MentorForm';
import StudentForm from './components/StudentForm';
import AdminForm from './components/AdminForm';
import BatchForm from './components/BatchForm';

function App() {
  return (
    <div className="app">
      <h1>Student Mentoring App</h1>
      <LoginForm />
      <MentorForm />
      <StudentForm />
      <AdminForm />
      <BatchForm />
    </div>
  );
}

export default App;