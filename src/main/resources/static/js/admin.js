document.addEventListener('DOMContentLoaded', function() {
    const user = checkAuth();
    if (!user || user.role !== 'admin') {
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
    
    // Load dashboard data
    if (document.getElementById('totalStudents')) {
        loadDashboardData();
    }
    
    // Load students table
    if (document.getElementById('studentsTable')) {
        loadStudents();
        setupStudentModal();
        setupAssignMentorModal();
    }
    
    // Load mentors table
    if (document.getElementById('mentorsTable')) {
        loadMentors();
        setupMentorModal();
    }
    
    // Load batches table
    if (document.getElementById('batchesTable')) {
        loadBatches();
        setupBatchModal();
    }
});

function loadDashboardData() {
    // Load total students count
    fetch('/api/students')
        .then(response => response.json())
        .then(students => {
            document.getElementById('totalStudents').textContent = students.length;
        });
    
    // Load total mentors count
    fetch('/api/mentors')
        .then(response => response.json())
        .then(mentors => {
            document.getElementById('totalMentors').textContent = mentors.length;
        });
    
    // Load total batches count
    fetch('/api/batches')
        .then(response => response.json())
        .then(batches => {
            document.getElementById('totalBatches').textContent = batches.length;
        });
}

function loadStudents() {
    fetch('/api/students')
        .then(response => response.json())
        .then(students => {
            const tbody = document.querySelector('#studentsTable tbody');
            tbody.innerHTML = '';
            
            students.forEach(student => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${student._id}</td>
                    <td>${student.name || 'N/A'}</td>
                    <td>${student.email || 'N/A'}</td>
                    <td>${student.mentor || 'Not assigned'}</td>
                    <td>${student.batch || 'Not assigned'}</td>
                    <td class="action-buttons">
                        <button class="action-btn edit-btn" data-id="${student._id}">Edit</button>
                        <button class="action-btn delete-btn" data-id="${student._id}">Delete</button>
                        <button class="action-btn assign-btn" data-email="${student.email}">Assign Mentor</button>
                    </td>
                `;
                tbody.appendChild(row);
            });
            
            // Add event listeners to action buttons
            document.querySelectorAll('.edit-btn').forEach(btn => {
                btn.addEventListener('click', function() {
                    editStudent(this.getAttribute('data-id'));
                });
            });
            
            document.querySelectorAll('.delete-btn').forEach(btn => {
                btn.addEventListener('click', function() {
                    deleteStudent(this.getAttribute('data-id'));
                });
            });
            
            document.querySelectorAll('.assign-btn').forEach(btn => {
                btn.addEventListener('click', function() {
                    openAssignMentorModal(this.getAttribute('data-email'));
                });
            });
        });
}

function setupStudentModal() {
    const modal = document.getElementById('studentModal');
    const addBtn = document.getElementById('addStudentBtn');
    const closeBtn = modal.querySelector('.close');
    const form = document.getElementById('studentForm');
    const message = document.getElementById('studentMessage');
    
    if (addBtn) {
        addBtn.addEventListener('click', function() {
            document.getElementById('modalTitle').textContent = 'Add New Student';
            document.getElementById('studentId').value = '';
            form.reset();
            modal.style.display = 'block';
        });
    }
    
    closeBtn.addEventListener('click', function() {
        modal.style.display = 'none';
    });
    
    window.addEventListener('click', function(e) {
        if (e.target === modal) {
            modal.style.display = 'none';
        }
    });
    
    form.addEventListener('submit', function(e) {
        e.preventDefault();
        
        const studentId = document.getElementById('studentId').value;
        const studentData = {
            name: document.getElementById('studentName').value,
            email: document.getElementById('studentEmail').value,
            mentor: document.getElementById('studentMentor').value,
            batch: document.getElementById('studentBatch').value
        };
        
        const url = studentId ? `/api/students/${studentId}` : '/api/students';
        const method = studentId ? 'PUT' : 'POST';
        
        fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(studentData)
        })
        .then(response => response.json())
        .then(data => {
            showSuccess('studentMessage', studentId ? 'Student updated successfully!' : 'Student added successfully!');
            loadStudents();
            setTimeout(() => {
                modal.style.display = 'none';
            }, 1500);
        })
        .catch(error => {
            showError('studentMessage', 'Error saving student: ' + error.message);
        });
    });
    
    // Load mentors and batches for dropdowns
    loadMentorsForDropdown();
    loadBatchesForDropdown();
}

function editStudent(id) {
    fetch(`/api/students/${id}`)
        .then(response => response.json())
        .then(student => {
            document.getElementById('modalTitle').textContent = 'Edit Student';
            document.getElementById('studentId').value = student._id;
            document.getElementById('studentName').value = student.name || '';
            document.getElementById('studentEmail').value = student.email || '';
            document.getElementById('studentMentor').value = student.mentor || '';
            document.getElementById('studentBatch').value = student.batch || '';
            
            document.getElementById('studentModal').style.display = 'block';
        });
}

function deleteStudent(id) {
    if (confirm('Are you sure you want to delete this student?')) {
        fetch(`/api/students/${id}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.ok) {
                loadStudents();
            } else {
                throw new Error('Failed to delete student');
            }
        })
        .catch(error => {
            alert('Error deleting student: ' + error.message);
        });
    }
}

function setupAssignMentorModal() {
    const modal = document.getElementById('assignMentorModal');
    const closeBtn = modal.querySelector('.close');
    const form = document.getElementById('assignMentorForm');
    const message = document.getElementById('assignMentorMessage');
    
    closeBtn.addEventListener('click', function() {
        modal.style.display = 'none';
    });
    
    window.addEventListener('click', function(e) {
        if (e.target === modal) {
            modal.style.display = 'none';
        }
    });
    
    form.addEventListener('submit', function(e) {
        e.preventDefault();
        
        const studentEmail = document.getElementById('assignStudentEmail').value;
        const mentorName = document.getElementById('mentorToAssign').value;
        
        fetch('/api/admin/assign-mentor', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                studentEmail: studentEmail,
                mentorName: mentorName
            })
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                showSuccess('assignMentorMessage', 'Mentor assigned successfully!');
                loadStudents();
                setTimeout(() => {
                    modal.style.display = 'none';
                }, 1500);
            } else {
                showError('assignMentorMessage', data.message || 'Failed to assign mentor');
            }
        })
        .catch(error => {
            showError('assignMentorMessage', 'Error assigning mentor: ' + error.message);
        });
    });
    
    // Load mentors for dropdown
    loadMentorsForDropdown('mentorToAssign');
}

function openAssignMentorModal(email) {
    document.getElementById('assignStudentEmail').value = email;
    document.getElementById('assignMentorModal').style.display = 'block';
}

function loadMentors() {
    fetch('/api/mentors')
        .then(response => response.json())
        .then(mentors => {
            const tbody = document.querySelector('#mentorsTable tbody');
            tbody.innerHTML = '';
            
            mentors.forEach(mentor => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${mentor._id}</td>
                    <td>${mentor.name || 'N/A'}</td>
                    <td>${mentor.email || 'N/A'}</td>
                    <td>${mentor.expertise || 'N/A'}</td>
                    <td>${mentor.students ? mentor.students.length : 0}</td>
                    <td class="action-buttons">
                        <button class="action-btn edit-btn" data-id="${mentor._id}">Edit</button>
                        <button class="action-btn delete-btn" data-id="${mentor._id}">Delete</button>
                    </td>
                `;
                tbody.appendChild(row);
            });
            
            // Add event listeners to action buttons
            document.querySelectorAll('.edit-btn').forEach(btn => {
                btn.addEventListener('click', function() {
                    editMentor(this.getAttribute('data-id'));
                });
            });
            
            document.querySelectorAll('.delete-btn').forEach(btn => {
                btn.addEventListener('click', function() {
                    deleteMentor(this.getAttribute('data-id'));
                });
            });
        });
}

function setupMentorModal() {
    const modal = document.getElementById('mentorModal');
    const addBtn = document.getElementById('addMentorBtn');
    const closeBtn = modal.querySelector('.close');
    const form = document.getElementById('mentorForm');
    const message = document.getElementById('mentorMessage');
    
    if (addBtn) {
        addBtn.addEventListener('click', function() {
            document.getElementById('mentorModalTitle').textContent = 'Add New Mentor';
            document.getElementById('mentorId').value = '';
            form.reset();
            modal.style.display = 'block';
        });
    }
    
    closeBtn.addEventListener('click', function() {
        modal.style.display = 'none';
    });
    
    window.addEventListener('click', function(e) {
        if (e.target === modal) {
            modal.style.display = 'none';
        }
    });
    
    form.addEventListener('submit', function(e) {
        e.preventDefault();
        
        const mentorId = document.getElementById('mentorId').value;
        const mentorData = {
            name: document.getElementById('mentorName').value,
            email: document.getElementById('mentorEmail').value,
            expertise: document.getElementById('mentorExpertise').value
        };
        
        const url = mentorId ? `/api/mentors/${mentorId}` : '/api/mentors';
        const method = mentorId ? 'PUT' : 'POST';
        
        fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(mentorData)
        })
        .then(response => response.json())
        .then(data => {
            showSuccess('mentorMessage', mentorId ? 'Mentor updated successfully!' : 'Mentor added successfully!');
            loadMentors();
            setTimeout(() => {
                modal.style.display = 'none';
            }, 1500);
        })
        .catch(error => {
            showError('mentorMessage', 'Error saving mentor: ' + error.message);
        });
    });
}

function editMentor(id) {
    fetch(`/api/mentors/${id}`)
        .then(response => response.json())
        .then(mentor => {
            document.getElementById('mentorModalTitle').textContent = 'Edit Mentor';
            document.getElementById('mentorId').value = mentor._id;
            document.getElementById('mentorName').value = mentor.name || '';
            document.getElementById('mentorEmail').value = mentor.email || '';
            document.getElementById('mentorExpertise').value = mentor.expertise || '';
            
            document.getElementById('mentorModal').style.display = 'block';
        });
}

function deleteMentor(id) {
    if (confirm('Are you sure you want to delete this mentor?')) {
        fetch(`/api/mentors/${id}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.ok) {
                loadMentors();
            } else {
                throw new Error('Failed to delete mentor');
            }
        })
        .catch(error => {
            alert('Error deleting mentor: ' + error.message);
        });
    }
}

function loadMentorsForDropdown(dropdownId = 'studentMentor') {
    fetch('/api/mentors')
        .then(response => response.json())
        .then(mentors => {
            const dropdown = document.getElementById(dropdownId);
            dropdown.innerHTML = '<option value="">Select Mentor</option>';
            
            mentors.forEach(mentor => {
                const option = document.createElement('option');
                option.value = mentor.name;
                option.textContent = mentor.name;
                dropdown.appendChild(option);
            });
        });
}

function loadBatches() {
    fetch('/api/batches')
        .then(response => response.json())
        .then(batches => {
            const tbody = document.querySelector('#batchesTable tbody');
            tbody.innerHTML = '';
            
            batches.forEach(batch => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${batch._id}</td>
                    <td>${batch.name || 'N/A'}</td>
                    <td>${batch.startDate ? new Date(batch.startDate).toLocaleDateString() : 'N/A'}</td>
                    <td>${batch.endDate ? new Date(batch.endDate).toLocaleDateString() : 'N/A'}</td>
                    <td>${batch.students ? batch.students.length : 0}</td>
                    <td class="action-buttons">
                        <button class="action-btn edit-btn" data-id="${batch._id}">Edit</button>
                        <button class="action-btn delete-btn" data-id="${batch._id}">Delete</button>
                    </td>
                `;
                tbody.appendChild(row);
            });
            
            // Add event listeners to action buttons
            document.querySelectorAll('.edit-btn').forEach(btn => {
                btn.addEventListener('click', function() {
                    editBatch(this.getAttribute('data-id'));
                });
            });
            
            document.querySelectorAll('.delete-btn').forEach(btn => {
                btn.addEventListener('click', function() {
                    deleteBatch(this.getAttribute('data-id'));
                });
            });
        });
}

function setupBatchModal() {
    const modal = document.getElementById('batchModal');
    const addBtn = document.getElementById('addBatchBtn');
    const closeBtn = modal.querySelector('.close');
    const form = document.getElementById('batchForm');
    const message = document.getElementById('batchMessage');
    
    if (addBtn) {
        addBtn.addEventListener('click', function() {
            document.getElementById('batchModalTitle').textContent = 'Add New Batch';
            document.getElementById('batchId').value = '';
            form.reset();
            modal.style.display = 'block';
        });
    }
    
    closeBtn.addEventListener('click', function() {
        modal.style.display = 'none';
    });
    
    window.addEventListener('click', function(e) {
        if (e.target === modal) {
            modal.style.display = 'none';
        }
    });
    
    form.addEventListener('submit', function(e) {
        e.preventDefault();
        
        const batchId = document.getElementById('batchId').value;
        const batchData = {
            name: document.getElementById('batchName').value,
            startDate: document.getElementById('batchStartDate').value,
            endDate: document.getElementById('batchEndDate').value
        };
        
        const url = batchId ? `/api/batches/${batchId}` : '/api/batches';
        const method = batchId ? 'PUT' : 'POST';
        
        fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(batchData)
        })
        .then(response => response.json())
        .then(data => {
            showSuccess('batchMessage', batchId ? 'Batch updated successfully!' : 'Batch added successfully!');
            loadBatches();
            setTimeout(() => {
                modal.style.display = 'none';
            }, 1500);
        })
        .catch(error => {
            showError('batchMessage', 'Error saving batch: ' + error.message);
        });
    });
}

function editBatch(id) {
    fetch(`/api/batches/${id}`)
        .then(response => response.json())
        .then(batch => {
            document.getElementById('batchModalTitle').textContent = 'Edit Batch';
            document.getElementById('batchId').value = batch._id;
            document.getElementById('batchName').value = batch.name || '';
            
            // Format dates for input fields
            const startDate = batch.startDate ? new Date(batch.startDate).toISOString().split('T')[0] : '';
            const endDate = batch.endDate ? new Date(batch.endDate).toISOString().split('T')[0] : '';
            
            document.getElementById('batchStartDate').value = startDate;
            document.getElementById('batchEndDate').value = endDate;
            
            document.getElementById('batchModal').style.display = 'block';
        });
}

function deleteBatch(id) {
    if (confirm('Are you sure you want to delete this batch?')) {
        fetch(`/api/batches/${id}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.ok) {
                loadBatches();
            } else {
                throw new Error('Failed to delete batch');
            }
        })
        .catch(error => {
            alert('Error deleting batch: ' + error.message);
        });
    }
}

function loadBatchesForDropdown() {
    fetch('/api/batches')
        .then(response => response.json())
        .then(batches => {
            const dropdown = document.getElementById('studentBatch');
            dropdown.innerHTML = '<option value="">Select Batch</option>';
            
            batches.forEach(batch => {
                const option = document.createElement('option');
                option.value = batch.name;
                option.textContent = batch.name;
                dropdown.appendChild(option);
            });
        });
}