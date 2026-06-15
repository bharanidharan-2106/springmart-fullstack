import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-merchant-inventory',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="card border-0 shadow-sm">
      <div class="card-header bg-white border-0 pt-4 pb-0">
        <h4 class="fw-bold mb-0">Inventory Management</h4>
      </div>
      <div class="card-body">
        <p class="text-muted">Track your stock levels across all your products.</p>
        <div class="table-responsive mt-4">
          <table class="table table-hover align-middle">
            <thead class="table-light">
              <tr>
                <th>SKU</th>
                <th>Product Name</th>
                <th>In Stock</th>
                <th>Status</th>
                <th>Update Stock</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>EL-WH-001</td>
                <td>Wireless Headphones</td>
                <td>45</td>
                <td><span class="badge bg-success">In Stock</span></td>
                <td>
                  <div class="input-group input-group-sm w-50">
                    <input type="number" class="form-control" value="45">
                    <button class="btn btn-outline-secondary" type="button">Update</button>
                  </div>
                </td>
              </tr>
              <tr>
                <td>EL-SW-002</td>
                <td>Smart Watch V2</td>
                <td>2</td>
                <td><span class="badge bg-danger">Low Stock</span></td>
                <td>
                  <div class="input-group input-group-sm w-50">
                    <input type="number" class="form-control" value="2">
                    <button class="btn btn-outline-secondary" type="button">Update</button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  `
})
export class MerchantInventoryComponent {}
