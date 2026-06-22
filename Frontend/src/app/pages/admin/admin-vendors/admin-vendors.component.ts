import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserService, User } from '../../../services/user.service';

@Component({
  selector: 'app-admin-vendors',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="card border-0 shadow-sm">
      <div class="card-header bg-white border-0 pt-4 pb-0">
        <h4 class="fw-bold mb-0">Vendor Approval</h4>
      </div>
      <div class="card-body">
        <p class="text-muted">Review and approve new merchant applications.</p>
        <div class="table-responsive mt-4">
          <table class="table table-hover align-middle">
            <thead class="table-light">
              <tr>
                <th>Name</th>
                <th>Email</th>
                <th>Status</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody *ngIf="vendors.length > 0; else noVendors">
              <tr *ngFor="let vendor of vendors">
                <td>{{ vendor.firstName }} {{ vendor.lastName }}</td>
                <td>{{ vendor.email }}</td>
                <td>
                  <span class="badge" [ngClass]="{
                    'bg-warning text-dark': vendor.status === 'PENDING',
                    'bg-success': vendor.status === 'APPROVED',
                    'bg-danger': vendor.status === 'REJECTED'
                  }">{{ vendor.status }}</span>
                </td>
                <td *ngIf="vendor.status === 'PENDING'">
                  <button class="btn btn-sm btn-success me-2" (click)="updateStatus(vendor.uuid, 'APPROVED')">Approve</button>
                  <button class="btn btn-sm btn-danger" (click)="updateStatus(vendor.uuid, 'REJECTED')">Reject</button>
                </td>
              </tr>
            </tbody>
            <ng-template #noVendors>
              <tr>
                <td colspan="4" class="text-center text-muted py-4">No pending vendors</td>
              </tr>
            </ng-template>
          </table>
        </div>
      </div>
    </div>
  `
})
export class AdminVendorsComponent implements OnInit {
  vendors: User[] = [];

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.loadVendors();
  }

  loadVendors(): void {
    this.userService.getMerchants().subscribe({
      next: (data) => {
        this.vendors = data;
      },
      error: (err) => {
        console.error('Error loading vendors:', err);
      }
    });
  }

  updateStatus(uuid: string, status: string): void {
    this.userService.updateUserStatus(uuid, status).subscribe({
      next: () => {
        this.loadVendors();
      },
      error: (err) => {
        console.error('Error updating status:', err);
      }
    });
  }
}
