import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { CartService } from '../../services/cart.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-cart-icon',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <a routerLink="/cart" class="cart-icon-container relative inline-flex items-center p-2 text-gray-700 hover:text-blue-600 transition-colors">
      <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z" />
      </svg>
      <span *ngIf="itemCount > 0" class="absolute top-0 right-0 inline-flex items-center justify-center px-2 py-1 text-xs font-bold leading-none text-white transform translate-x-1/4 -translate-y-1/4 bg-red-600 rounded-full">
        {{ itemCount }}
      </span>
    </a>
  `,
  styles: [`
    .cart-icon-container {
      cursor: pointer;
    }
  `]
})
export class CartIconComponent implements OnInit, OnDestroy {
  itemCount: number = 0;
  private subscription: Subscription = new Subscription();

  constructor(private cartService: CartService) {}

  ngOnInit(): void {
    // Initial fetch
    this.cartService.getSummary().subscribe();
    
    // Subscribe to changes
    this.subscription = this.cartService.cartSummary$.subscribe(summary => {
      this.itemCount = summary.totalItems;
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
