import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-merchant-orders',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="card border-0 shadow-sm">
      <div class="card-header bg-white border-0 pt-4 pb-0">
        <h4 class="fw-bold mb-0">Order Processing</h4>
      </div>
      <div class="card-body">
        <p class="text-muted">View and manage customer orders.</p>
        <div class="table-responsive mt-4">
          <table class="table table-hover align-middle">
            <thead class="table-light">
              <tr>
                <th>Order ID</th>
                <th>Date</th>
                <th>Customer</th>
                <th>Total</th>
                <th>Status</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>#ORD-8924</td>
                <td>Today, 10:45 AM</td>
                <td>John Doe</td>
                <td>$124.50</td>
                <td><span class="badge bg-warning text-dark">Pending</span></td>
                <td>
                  <button class="btn btn-sm btn-primary">Process Order</button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  `
})
export class MerchantOrdersComponent {}
