import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { SignupComponent } from './pages/signup/signup.component';
import { ProductListComponent } from './pages/products/product-list.component';
import { CartPageComponent } from './pages/cart/cart-page.component';

// Admin Components
import { AdminLayoutComponent } from './pages/admin/admin-layout/admin-layout.component';
import { AdminDashboardComponent } from './pages/admin/admin-dashboard/admin-dashboard.component';
import { AdminUsersComponent } from './pages/admin/admin-users/admin-users.component';
import { AdminVendorsComponent } from './pages/admin/admin-vendors/admin-vendors.component';
import { AdminProductsComponent } from './pages/admin/admin-products/admin-products.component';

// Merchant Components
import { MerchantLayoutComponent } from './pages/merchant/merchant-layout/merchant-layout.component';
import { MerchantDashboardComponent } from './pages/merchant/merchant-dashboard/merchant-dashboard.component';
import { MerchantProductsComponent } from './pages/merchant/merchant-products/merchant-products.component';
import { MerchantInventoryComponent } from './pages/merchant/merchant-inventory/merchant-inventory.component';
import { MerchantOrdersComponent } from './pages/merchant/merchant-orders/merchant-orders.component';

import { ProfileComponent } from './pages/profile/profile.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'products', component: ProductListComponent },
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'cart', component: CartPageComponent },
  
  // Admin Routes
  { 
    path: 'admin', 
    component: AdminLayoutComponent,
    children: [
      { path: 'dashboard', component: AdminDashboardComponent },
      { path: 'users', component: AdminUsersComponent },
      { path: 'vendors', component: AdminVendorsComponent },
      { path: 'products', component: AdminProductsComponent },
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
    ]
  },

  // Merchant Routes
  { 
    path: 'merchant', 
    component: MerchantLayoutComponent,
    children: [
      { path: 'dashboard', component: MerchantDashboardComponent },
      { path: 'products', component: MerchantProductsComponent },
      { path: 'inventory', component: MerchantInventoryComponent },
      { path: 'orders', component: MerchantOrdersComponent },
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
    ]
  },

  { path: '**', redirectTo: '' }
];
