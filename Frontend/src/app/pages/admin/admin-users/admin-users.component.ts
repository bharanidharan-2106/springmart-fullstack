import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserService, User } from '../../../services/user.service';

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
        <div *ngIf="errorMessage" class="alert alert-danger py-2">{{ errorMessage }}</div>
        <div class="table-responsive mt-4">
          <table class="table table-hover align-middle">
            <thead class="table-light">
              <tr>
                <th>Name</th>
                <th>Email</th>
                <th>Role</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody *ngIf="users.length > 0; else noUsers">
              <tr *ngFor="let user of users">
                <td>{{ user.firstName }} {{ user.lastName }}</td>
                <td>{{ user.email }}</td>
                <td>
                  <span class="badge" [ngClass]="{
                    'bg-primary': user.role === 'Customer',
                    'bg-success': user.role === 'Merchant',
                    'bg-dark': user.role === 'Admin'
                  }">{{ user.role }}</span>
                </td>
                <td>
                  <span class="badge" [ngClass]="{
                    'bg-success bg-opacity-10 text-success': user.status === 'APPROVED',
                    'bg-warning text-dark': user.status === 'PENDING',
                    'bg-danger bg-opacity-10 text-danger': user.status === 'REJECTED'
                  }">{{ user.status }}</span>
                </td>
              </tr>
            </tbody>
            <ng-template #noUsers>
              <tr>
                <td colspan="4" class="text-center text-muted py-4">No users found in the database.</td>
              </tr>
            </ng-template>
          </table>
        </div>
      </div>
    </div>
  `
})
export class AdminUsersComponent implements OnInit {
  users: User[] = [];
  errorMessage: string | null = null;

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.userService.getAllUsers().subscribe({
      next: (data) => {
        this.users = data;
      },
      error: (err) => {
        console.error('Error loading users:', err);
        this.errorMessage = 'Failed to load users. Please ensure you are logged in as admin.';
      }
    });
  }
}
