import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

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
                <th>Store Name</th>
                <th>Owner</th>
                <th>Applied Date</th>
                <th>Status</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>Tech Haven</td>
                <td>Alice Smith</td>
                <td>Oct 24, 2026</td>
                <td><span class="badge bg-warning text-dark">Pending</span></td>
                <td>
                  <button class="btn btn-sm btn-success me-2">Approve</button>
                  <button class="btn btn-sm btn-danger">Reject</button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  `
})
export class AdminVendorsComponent {}
