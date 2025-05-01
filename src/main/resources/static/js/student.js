document.addEventListener('DOMContentLoaded', function() {
    const user = checkAuth();
    if (!user || user.role !== 'student') {
        window.location.href = '../login.html';
        return;
    }
    
    // Set user email in header
    document.getElementById('userEmail').textContent = user.email;
    
    // Logout functionality
    document.getElementById('logout').addEventListener('click', function(e) {
        e.preventDefault();
        logout();
    });
    
    // Load student data
    loadStudentData(user.email);
});

function loadStudentData(email) {
    fetch('/api/students')
        .then(response => response.json())
        .then(students => {
            const student = students.find(s => s.email === email);
            if (student) {
                // Display student info
                document.getElementById('studentName').textContent = student.name || 'Student';
                document.getElementById('studentBatch').textContent = `Batch: ${student.batch || 'Not assigned'}`;
                document.getElementById('studentMentor').textContent = `Mentor: ${student.mentor || 'Not assigned'}`;
            }
        });
}