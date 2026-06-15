import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-merchant-dashboard',
  standalone: true,
  imports: [CommonModule, MatIconModule],
  templateUrl: './merchant-dashboard.component.html',
  styleUrl: './merchant-dashboard.component.scss'
})
export class MerchantDashboardComponent {
  stats = [
    { label: 'Total Sales', value: '$12,450', icon: 'payments', color: 'bg-success' },
    { label: 'Active Orders', value: '34', icon: 'local_shipping', color: 'bg-primary' },
    { label: 'Products Low Stock', value: '5', icon: 'warning', color: 'bg-warning' },
    { label: 'Total Products', value: '128', icon: 'inventory_2', color: 'bg-info' }
  ];
}
