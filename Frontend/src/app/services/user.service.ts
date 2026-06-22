import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface User {
  id: number;
  uuid: string;
  firstName: string;
  lastName: string;
  email: string;
  role: string;
  status: string;
}

export interface DashboardStats {
  totalUsers: number;
  activeVendors: number;
  pendingVendors: number;
  productsPending: number;
  platformRevenue: number;
  totalProducts: number;
  totalSales: number;
  activeOrders: number;
  productsLowStock: number;
}

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl = 'http://localhost:8080/users';

  constructor(private http: HttpClient) { }

  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.baseUrl);
  }

  getMerchants(status?: string): Observable<User[]> {
    let url = `${this.baseUrl}/merchants`;
    if (status) {
      url += `?status=${status}`;
    }
    return this.http.get<User[]>(url);
  }

  updateUserStatus(uuid: string, status: string): Observable<User> {
    return this.http.put<User>(`${this.baseUrl}/${uuid}/status`, { status });
  }

  getDashboardStats(): Observable<DashboardStats> {
    return this.http.get<DashboardStats>(`${this.baseUrl}/dashboard/stats`);
  }
}
