import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, MatIconModule],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.scss'
})
export class AdminDashboardComponent {
  stats = [
    { label: 'Total Users', value: '1,245', icon: 'group', color: 'bg-primary' },
    { label: 'Active Vendors', value: '48', icon: 'store', color: 'bg-success' },
    { label: 'Products Pending', value: '156', icon: 'inventory_2', color: 'bg-warning' },
    { label: 'Platform Revenue', value: '$45k', icon: 'payments', color: 'bg-info' }
  ];
}
