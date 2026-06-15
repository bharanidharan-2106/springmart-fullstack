import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-merchant-products',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="card border-0 shadow-sm">
      <div class="card-header bg-white border-0 pt-4 pb-0 d-flex justify-content-between align-items-center">
        <h4 class="fw-bold mb-0">My Products</h4>
        <button class="btn btn-danger">Add Product</button>
      </div>
      <div class="card-body">
        <p class="text-muted">Manage your product catalog, update details, or delete listings.</p>
        <div class="table-responsive mt-4">
          <table class="table table-hover align-middle">
            <thead class="table-light">
              <tr>
                <th>Product Name</th>
                <th>Category</th>
                <th>Price</th>
                <th>Status</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>Wireless Headphones</td>
                <td>Electronics</td>
                <td>$99.99</td>
                <td><span class="badge bg-success bg-opacity-10 text-success">Active</span></td>
                <td>
                  <button class="btn btn-sm btn-outline-secondary me-2">Edit</button>
                  <button class="btn btn-sm btn-outline-danger">Delete</button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  `
})
export class MerchantProductsComponent {}
