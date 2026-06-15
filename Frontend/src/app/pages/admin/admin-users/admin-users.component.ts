import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-admin-users',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="card border-0 shadow-sm">
      <div class="card-header bg-white border-0 pt-4 pb-0">
        <h4 class="fw-bold mb-0">User Management</h4>
      </div>
      <div class="card-body">
        <p class="text-muted">Manage all registered users, roles, and permissions here.</p>
        <!-- Data Table Placeholder -->
        <div class="table-responsive mt-4">
          <table class="table table-hover align-middle">
            <thead class="table-light">
              <tr>
                <th>Name</th>
                <th>Email</th>
                <th>Role</th>
                <th>Status</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>John Doe</td>
                <td>john&#64;example.com</td>
                <td><span class="badge bg-primary">Customer</span></td>
                <td><span class="badge bg-success bg-opacity-10 text-success">Active</span></td>
                <td>
                  <button class="btn btn-sm btn-outline-secondary me-2">Edit</button>
                  <button class="btn btn-sm btn-outline-danger">Block</button>
                </td>
              </tr>
              <!-- More rows -->
            </tbody>
          </table>
        </div>
      </div>
    </div>
  `
})
export class AdminUsersComponent {}
