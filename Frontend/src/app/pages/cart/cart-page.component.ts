import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { CartService, CartResponse } from '../../services/cart.service';
import { CartItemComponent } from '../../components/cart-item/cart-item.component';

@Component({
  selector: 'app-cart-page',
  standalone: true,
  imports: [CommonModule, RouterModule, CartItemComponent],
  template: `
    <div class="container mx-auto px-4 py-8 max-w-6xl">
      <h1 class="text-3xl font-bold mb-8">Shopping Cart</h1>
      
      <div *ngIf="loading" class="flex justify-center py-12">
        <div class="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
      </div>
      
      <div *ngIf="!loading && (!cart || cart.items.length === 0)" class="text-center py-16 bg-gray-50 rounded-lg">
        <svg xmlns="http://www.w3.org/2000/svg" class="h-24 w-24 mx-auto text-gray-400 mb-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1" d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z" />
        </svg>
        <h2 class="text-2xl font-semibold text-gray-700 mb-2">Your cart is empty</h2>
        <p class="text-gray-500 mb-6">Looks like you haven't added anything yet.</p>
        <a routerLink="/" class="inline-block bg-blue-600 text-white px-6 py-3 rounded-lg font-medium hover:bg-blue-700 transition-colors">
          Continue Shopping
        </a>
      </div>

      <div *ngIf="!loading && cart && cart.items.length > 0" class="flex flex-col lg:flex-row gap-8">
        <!-- Cart Items List -->
        <div class="lg:w-2/3">
          <div class="bg-white shadow rounded-lg overflow-hidden">
            <div class="p-4 border-b bg-gray-50 flex justify-between items-center">
              <h2 class="text-lg font-semibold text-gray-700">Items ({{ cart.totalItems }})</h2>
              <button (click)="clearCart()" class="text-sm text-red-600 hover:text-red-800 font-medium">Clear Cart</button>
            </div>
            <div>
              <app-cart-item 
                *ngFor="let item of cart.items" 
                [item]="item"
                (updateQuantity)="onUpdateQuantity($event)"
                (remove)="onRemoveItem($event)">
              </app-cart-item>
            </div>
          </div>
        </div>

        <!-- Order Summary -->
        <div class="lg:w-1/3">
          <div class="bg-white shadow rounded-lg p-6 sticky top-24">
            <h2 class="text-xl font-bold mb-4 border-b pb-4">Order Summary</h2>
            
            <div class="space-y-3 mb-6">
              <div class="flex justify-between text-gray-600">
                <span>Subtotal ({{ cart.totalQuantity }} items)</span>
                <span>{{ cart.totalAmount | currency }}</span>
              </div>
              <div class="flex justify-between text-gray-600">
                <span>Shipping</span>
                <span class="text-green-600">Free</span>
              </div>
            </div>
            
            <div class="border-t pt-4 mb-6">
              <div class="flex justify-between items-center">
                <span class="text-lg font-bold">Total</span>
                <span class="text-2xl font-bold text-gray-900">{{ cart.totalAmount | currency }}</span>
              </div>
            </div>
            
            <button class="w-full bg-blue-600 text-white py-3 rounded-lg font-bold text-lg hover:bg-blue-700 transition-colors shadow-md">
              Proceed to Checkout
            </button>
            
            <div class="mt-4 text-center">
              <a routerLink="/" class="text-blue-600 hover:text-blue-800 font-medium text-sm inline-flex items-center">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4 mr-1" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18" />
                </svg>
                Continue Shopping
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
  `
})
export class CartPageComponent implements OnInit {
  cart: CartResponse | null = null;
  loading = true;

  constructor(private cartService: CartService) {}

  ngOnInit(): void {
    this.loadCart();
  }

  loadCart(): void {
    this.loading = true;
    this.cartService.getCart().subscribe({
      next: (cart) => {
        this.cart = cart;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error fetching cart', err);
        this.loading = false;
      }
    });
  }

  onUpdateQuantity(event: { id: number, quantity: number }): void {
    this.cartService.updateQuantity(event.id, event.quantity).subscribe({
      next: (cart) => {
        this.cart = cart;
      },
      error: (err) => {
        console.error('Error updating quantity', err);
      }
    });
  }

  onRemoveItem(cartItemId: number): void {
    this.cartService.removeItem(cartItemId).subscribe({
      next: (cart) => {
        this.cart = cart;
      },
      error: (err) => {
        console.error('Error removing item', err);
      }
    });
  }

  clearCart(): void {
    if (confirm('Are you sure you want to clear your cart?')) {
      this.cartService.clearCart().subscribe({
        next: () => {
          this.cart = null; // or empty structure
          this.loadCart();
        },
        error: (err) => {
          console.error('Error clearing cart', err);
        }
      });
    }
  }
}
