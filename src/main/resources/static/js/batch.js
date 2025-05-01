document.addEventListener('DOMContentLoaded', function() {
    const user = checkAuth();
    if (!user) {
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
    
    // Load batches
    loadBatches();
    setupBatchModal();
});

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