import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { UserService, DashboardStats } from '../../../services/user.service';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, MatIconModule],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.scss'
})
export class AdminDashboardComponent implements OnInit {
  stats: any[] = [];
  pendingVendors = 0;

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.loadStats();
  }

  loadStats(): void {
    this.userService.getDashboardStats().subscribe({
      next: (data) => {
        this.pendingVendors = data.pendingVendors ?? 0;
        this.stats = [
          { label: 'Total Users', value: data.totalUsers.toString(), icon: 'group', color: 'bg-primary' },
          { label: 'Active Vendors', value: data.activeVendors.toString(), icon: 'store', color: 'bg-success' },
          { label: 'Products Pending', value: data.productsPending.toString(), icon: 'inventory_2', color: 'bg-warning' },
          { label: 'Platform Revenue', value: '$' + data.platformRevenue.toLocaleString(), icon: 'payments', color: 'bg-info' }
        ];
      },
      error: (err) => {
        console.error('Error loading stats:', err);
        this.setDefaultStats();
      }
    });
  }

  setDefaultStats(): void {
    this.stats = [
      { label: 'Total Users', value: '0', icon: 'group', color: 'bg-primary' },
      { label: 'Active Vendors', value: '0', icon: 'store', color: 'bg-success' },
      { label: 'Products Pending', value: '0', icon: 'inventory_2', color: 'bg-warning' },
      { label: 'Platform Revenue', value: '$0', icon: 'payments', color: 'bg-info' }
    ];
  }
}
