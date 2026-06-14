import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-trending-products',
  standalone: true,
  imports: [CommonModule, MatIconModule],
  templateUrl: './trending-products.component.html',
  styleUrl: './trending-products.component.scss'
})
export class TrendingProductsComponent {
  products = [
    { name: 'iPhone 16 Pro Max', price: 1199.00, rating: 5, image: 'https://placehold.co/400x400/F5F5F5/1A1A1A?text=iPhone+16' },
    { name: 'Samsung Galaxy S25', price: 1099.00, rating: 4.5, image: 'https://placehold.co/400x400/F5F5F5/1A1A1A?text=Galaxy+S25' },
    { name: 'MacBook Pro M3', price: 1999.00, rating: 5, image: 'https://placehold.co/400x400/F5F5F5/1A1A1A?text=MacBook+Pro' },
    { name: 'Nike Air Max 2026', price: 150.00, rating: 4, image: 'https://placehold.co/400x400/F5F5F5/1A1A1A?text=Nike+Shoes' },
    { name: 'Apple Watch Ultra', price: 799.00, rating: 4.5, image: 'https://placehold.co/400x400/F5F5F5/1A1A1A?text=Smart+Watch' },
    { name: 'Sony WH-1000XM6', price: 349.00, rating: 5, image: 'https://placehold.co/400x400/F5F5F5/1A1A1A?text=Headphones' },
    { name: 'Logitech G Pro X Superlight', price: 159.00, rating: 4.5, image: 'https://placehold.co/400x400/F5F5F5/1A1A1A?text=Gaming+Mouse' },
    { name: 'JBL Charge 6', price: 179.00, rating: 4, image: 'https://placehold.co/400x400/F5F5F5/1A1A1A?text=Bluetooth+Speaker' }
  ];

  getStars(rating: number): number[] {
    return Array(Math.floor(rating)).fill(0);
  }

  hasHalfStar(rating: number): boolean {
    return rating % 1 !== 0;
  }
}
