document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('loginForm');
    const loginMessage = document.getElementById('loginMessage');
    
    if (loginForm) {
        loginForm.addEventListener('submit', function(e) {
            e.preventDefault();
            
            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;
            const role = document.getElementById('role').value;
            
            // Call login API
            fetch('/api/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    email: email,
                    password: password
                })
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    loginMessage.textContent = 'Login successful! Redirecting...';
                    loginMessage.className = 'message success';
                    
                    // Store user data in localStorage
                    localStorage.setItem('user', JSON.stringify({
                        email: email,
                        role: role
                    }));
                    
                    // Redirect based on role
                    setTimeout(() => {
                        if (role === 'admin') {
                            window.location.href = 'admin/dashboard.html';
                        } else if (role === 'mentor') {
                            window.location.href = 'mentor/dashboard.html';
                        } else if (role === 'student') {
                            window.location.href = 'student/dashboard.html';
                        }
                    }, 1000);
                } else {
                    loginMessage.textContent = 'Login failed: ' + (data.message || 'Invalid credentials');
                    loginMessage.className = 'message error';
                }
            })
            .catch(error => {
                loginMessage.textContent = 'An error occurred during login.';
                loginMessage.className = 'message error';
                console.error('Login error:', error);
            });
        });
    }
});