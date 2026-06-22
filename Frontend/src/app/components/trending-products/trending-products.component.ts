import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { ProductService, Product } from '../../services/product.service';

@Component({
  selector: 'app-trending-products',
  standalone: true,
  imports: [CommonModule, RouterLink, MatIconModule],
  templateUrl: './trending-products.component.html',
  styleUrl: './trending-products.component.scss'
})
export class TrendingProductsComponent implements OnInit {
  products: Product[] = [];

  constructor(public productService: ProductService) {}

  ngOnInit(): void {
    this.productService.getAllProducts(0, 8).subscribe({
      next: (response) => {
        this.products = response.data?.content || [];
      },
      error: (err) => {
        console.error('Error loading trending products:', err);
      }
    });
  }

  getStars(rating: number): number[] {
    return Array(Math.floor(rating || 0)).fill(0);
  }

  hasHalfStar(rating: number): boolean {
    return (rating || 0) % 1 !== 0;
  }
}
