import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { ProductService, Product } from '../../services/product.service';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule, RouterLink, MatIconModule],
  template: `
    <section class="product-list-page py-5 bg-white min-vh-100">
      <div class="container">
        <div class="d-flex justify-content-between align-items-center mb-4">
          <div>
            <a routerLink="/" class="text-decoration-none text-muted small d-inline-flex align-items-center gap-1 mb-2">
              <mat-icon class="fs-6">arrow_back</mat-icon> Back to Home
            </a>
            <h1 class="h3 fw-bold mb-0">{{ pageTitle }}</h1>
            <p class="text-muted mb-0" *ngIf="!isLoading">{{ products.length }} product(s) found</p>
          </div>
        </div>

        <div *ngIf="errorMessage" class="alert alert-danger">{{ errorMessage }}</div>

        <div *ngIf="isLoading" class="text-center py-5 text-muted">Loading products...</div>

        <div class="row g-4" *ngIf="!isLoading && products.length > 0">
          <div class="col-12 col-md-6 col-lg-3" *ngFor="let product of products">
            <div class="product-card card h-100 border-0 shadow-sm rounded-4 overflow-hidden">
              <div class="image-wrapper bg-light p-3 text-center">
                <img [src]="productService.getProductImage(product)" [alt]="product.productName" class="img-fluid rounded" style="max-height: 180px; object-fit: contain;" />
              </div>
              <div class="card-body p-4 d-flex flex-column">
                <span class="badge bg-light text-dark align-self-start mb-2">{{ product.categoryName || 'General' }}</span>
                <h3 class="h6 fw-bold text-dark mb-1 text-truncate">{{ product.productName }}</h3>
                <p class="text-muted small mb-2">{{ product.brandName }}</p>
                <div class="mt-auto d-flex justify-content-between align-items-center pt-2">
                  <span class="fs-5 fw-bold text-dark">{{ product.price | currency }}</span>
                  <span class="text-muted small">{{ product.quantity }} in stock</span>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div *ngIf="!isLoading && products.length === 0" class="text-center py-5 text-muted">
          <mat-icon class="display-4 mb-3 text-light">inventory_2</mat-icon>
          <p class="mb-0">No products found.</p>
        </div>
      </div>
    </section>
  `
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];
  pageTitle = 'All Products';
  isLoading = false;
  errorMessage: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    public productService: ProductService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const keyword = params['q']?.trim();
      const categoryId = params['categoryId'];
      const categoryName = params['categoryName'];

      if (categoryName) {
        this.pageTitle = categoryName;
      } else if (keyword) {
        this.pageTitle = `Search results for "${keyword}"`;
      } else {
        this.pageTitle = 'All Products';
      }

      this.loadProducts(keyword, categoryId);
    });
  }

  loadProducts(keyword?: string, categoryId?: string): void {
    this.isLoading = true;
    this.errorMessage = null;
    this.products = [];

    if (categoryId) {
      this.productService.getProductsByCategory(categoryId).subscribe({
        next: (response) => this.handleResponse(response.data?.content || []),
        error: (err) => this.handleError(err)
      });
      return;
    }

    if (keyword) {
      this.productService.searchProducts(keyword).subscribe({
        next: (response) => this.handleResponse(response.data?.content || []),
        error: (err) => this.handleError(err)
      });
      return;
    }

    this.productService.getAllProducts().subscribe({
      next: (response) => this.handleResponse(response.data?.content || []),
      error: (err) => this.handleError(err)
    });
  }

  private handleResponse(products: Product[]): void {
    this.products = products;
    this.isLoading = false;
  }

  private handleError(err: any): void {
    console.error('Error loading products:', err);
    this.errorMessage = 'Unable to load products. Please try again.';
    this.isLoading = false;
  }
}
