import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-admin-products',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="card border-0 shadow-sm">
      <div class="card-header bg-white border-0 pt-4 pb-0">
        <h4 class="fw-bold mb-0">Product Moderation</h4>
      </div>
      <div class="card-body">
        <p class="text-muted">Monitor and moderate products listed by merchants across the platform.</p>
        <div class="alert alert-info border-0 mt-3">
          <strong>Tip:</strong> Use filters to quickly find products reported by users.
        </div>
      </div>
    </div>
  `
})
export class AdminProductsComponent {}
