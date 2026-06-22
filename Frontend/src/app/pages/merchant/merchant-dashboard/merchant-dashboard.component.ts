import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { AuthService } from '../../../services/auth.service';
import { ProductService } from '../../../services/product.service';

@Component({
  selector: 'app-merchant-dashboard',
  standalone: true,
  imports: [CommonModule, MatIconModule],
  templateUrl: './merchant-dashboard.component.html',
  styleUrl: './merchant-dashboard.component.scss'
})
export class MerchantDashboardComponent implements OnInit {
  stats: any[] = [];
  totalProducts = 0;

  constructor(
    private authService: AuthService,
    private productService: ProductService
  ) {}

  ngOnInit(): void {
    this.loadStats();
  }

  loadStats(): void {
    const currentUser = this.authService.currentUserValue;
    if (currentUser?.uuid) {
      this.productService.getAllProductsByMerchantUuid(currentUser.uuid).subscribe({
        next: (response) => {
          this.totalProducts = response.data?.length || 0;
          this.setStats();
        },
        error: (err) => {
          console.error('Error loading products:', err);
          this.setStats();
        }
      });
    } else {
      this.setStats();
    }
  }

  setStats(): void {
    this.stats = [
      { label: 'Total Sales', value: '$0', icon: 'payments', color: 'bg-success' },
      { label: 'Active Orders', value: '0', icon: 'local_shipping', color: 'bg-primary' },
      { label: 'Products Low Stock', value: '0', icon: 'warning', color: 'bg-warning' },
      { label: 'Total Products', value: this.totalProducts.toString(), icon: 'inventory_2', color: 'bg-info' }
    ];
  }
}
