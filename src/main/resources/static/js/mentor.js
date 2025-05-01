document.addEventListener('DOMContentLoaded', function() {
    const user = checkAuth();
    if (!user || user.role !== 'mentor') {
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
    
    // Load mentor data
    loadMentorData(user.email);
});

function loadMentorData(email) {
    // Find mentor by email
    fetch('/api/mentors')
        .then(response => response.json())
        .then(mentors => {
            const mentor = mentors.find(m => m.email === email);
            if (mentor) {
                // Display mentor info
                document.getElementById('mentorName').textContent = mentor.name;
                document.getElementById('mentorExpertise').textContent = `Expertise: ${mentor.expertise || 'Not specified'}`;
                document.getElementById('mentorEmail').textContent = `Email: ${mentor.email}`;
                
                // Display student count
                const studentCount = mentor.students ? mentor.students.length : 0;
                document.getElementById('studentCount').textContent = studentCount;
                
                // Load assigned students
                if (mentor.students && mentor.students.length > 0) {
                    loadAssignedStudents(mentor.students);
                } else {
                    const tbody = document.querySelector('#studentsTable tbody');
                    tbody.innerHTML = '<tr><td colspan="4">No students assigned yet</td></tr>';
                }
            }
        });
}

function loadAssignedStudents(studentEmails) {
    fetch('/api/students')
        .then(response => response.json())
        .then(students => {
            const assignedStudents = students.filter(student => 
                studentEmails.includes(student.email)
            );
            
            const tbody = document.querySelector('#studentsTable tbody');
            tbody.innerHTML = '';
            
            if (assignedStudents.length === 0) {
                tbody.innerHTML = '<tr><td colspan="4">No students assigned yet</td></tr>';
                return;
            }
            
            assignedStudents.forEach(student => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${student.name || 'N/A'}</td>
                    <td>${student.email || 'N/A'}</td>
                    <td>${student.batch || 'Not assigned'}</td>
                    <td>Never</td>
                `;
                tbody.appendChild(row);
            });
        });
}