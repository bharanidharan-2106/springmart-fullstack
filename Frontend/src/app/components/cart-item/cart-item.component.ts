import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CartItem } from '../../services/cart.service';

@Component({
  selector: 'app-cart-item',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="flex items-center justify-between p-4 border-b">
      <div class="flex items-center space-x-4">
        <img [src]="item.productImage || 'assets/placeholder.png'" [alt]="item.productName" class="w-16 h-16 object-cover rounded">
        <div>
          <h3 class="text-lg font-semibold">{{ item.productName }}</h3>
          <p class="text-gray-500 text-sm">SKU: {{ item.sku }}</p>
          <p class="text-gray-700 font-medium">{{ item.price | currency }}</p>
        </div>
      </div>
      
      <div class="flex items-center space-x-6">
        <div class="flex items-center space-x-2 border rounded p-1">
          <button (click)="decreaseQuantity()" class="px-2 py-1 bg-gray-100 hover:bg-gray-200 rounded" [disabled]="item.quantity <= 1">-</button>
          <span class="w-8 text-center">{{ item.quantity }}</span>
          <button (click)="increaseQuantity()" class="px-2 py-1 bg-gray-100 hover:bg-gray-200 rounded">+</button>
        </div>
        
        <div class="text-lg font-bold w-24 text-right">
          {{ item.lineTotal | currency }}
        </div>
        
        <button (click)="onRemove()" class="text-red-500 hover:text-red-700 p-2">
          <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
            <path fill-rule="evenodd" d="M9 2a1 1 0 00-.894.553L7.382 4H4a1 1 0 000 2v10a2 2 0 002 2h8a2 2 0 002-2V6a1 1 0 100-2h-3.382l-.724-1.447A1 1 0 0011 2H9zM7 8a1 1 0 012 0v6a1 1 0 11-2 0V8zm5-1a1 1 0 00-1 1v6a1 1 0 102 0V8a1 1 0 00-1-1z" clip-rule="evenodd" />
          </svg>
        </button>
      </div>
    </div>
  `
})
export class CartItemComponent {
  @Input() item!: CartItem;
  @Output() updateQuantity = new EventEmitter<{ id: number, quantity: number }>();
  @Output() remove = new EventEmitter<number>();

  increaseQuantity() {
    this.updateQuantity.emit({ id: this.item.id, quantity: this.item.quantity + 1 });
  }

  decreaseQuantity() {
    if (this.item.quantity > 1) {
      this.updateQuantity.emit({ id: this.item.id, quantity: this.item.quantity - 1 });
    }
  }

  onRemove() {
    this.remove.emit(this.item.id);
  }
}
