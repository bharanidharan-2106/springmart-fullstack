import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '../../../services/auth.service';
import { ProductService, Product, Category, CreateProductRequest } from '../../../services/product.service';

@Component({
  selector: 'app-merchant-products',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  template: `
    <div class="card border-0 shadow-sm mb-4">
      <div class="card-header bg-white border-0 pt-4 pb-0 d-flex justify-content-between align-items-center">
        <h4 class="fw-bold mb-0">My Products</h4>
        <button class="btn btn-primary" (click)="showAddProductForm = !showAddProductForm">
          {{ showAddProductForm ? 'Cancel' : 'Add Product' }}
        </button>
      </div>
      <div class="card-body" *ngIf="showAddProductForm">
        <div *ngIf="errorMessage" class="alert alert-danger py-2">{{ errorMessage }}</div>
        <div *ngIf="successMessage" class="alert alert-success py-2">{{ successMessage }}</div>
        <form [formGroup]="productForm" (ngSubmit)="onAddProduct()">
          <div class="row">
            <div class="col-md-6 mb-3">
              <label class="form-label">Product Name</label>
              <input type="text" formControlName="productName" class="form-control" required>
            </div>
            <div class="col-md-6 mb-3">
              <label class="form-label">Category</label>
              <select formControlName="categoryId" class="form-select" required>
                <option value="">Select category</option>
                <option *ngFor="let category of categories" [value]="category.id">{{ category.name }}</option>
              </select>
            </div>
          </div>
          <div class="row">
            <div class="col-md-6 mb-3">
              <label class="form-label">Brand</label>
              <input type="text" formControlName="brandName" class="form-control" placeholder="Enter your brand name" required>
            </div>
            <div class="col-md-6 mb-3">
              <label class="form-label">Price</label>
              <input type="number" formControlName="price" class="form-control" step="0.01" min="0.01" required>
            </div>
          </div>
          <div class="row">
            <div class="col-md-6 mb-3">
              <label class="form-label">Quantity</label>
              <input type="number" formControlName="quantity" class="form-control" min="0" required>
            </div>
            <div class="col-md-6 mb-3">
              <label class="form-label">Product Photo</label>
              <input type="file" class="form-control" accept="image/*" (change)="onImageSelected($event)">
              <div *ngIf="imagePreview" class="mt-2">
                <img [src]="imagePreview" alt="Preview" class="img-thumbnail" style="max-height: 120px;">
              </div>
            </div>
          </div>
          <div class="mb-3">
            <label class="form-label">Description</label>
            <textarea formControlName="description" class="form-control" rows="3"></textarea>
          </div>
          <button type="submit" class="btn btn-success" [disabled]="productForm.invalid || isSubmitting || !imagePreview">
            {{ isSubmitting ? 'Adding...' : 'Add Product' }}
          </button>
        </form>
      </div>
      <div class="card-body">
        <p class="text-muted">Manage your product catalog, update details, or delete listings.</p>
        <div class="table-responsive mt-4">
          <table class="table table-hover align-middle">
            <thead class="table-light">
              <tr>
                <th>Photo</th>
                <th>Product Name</th>
                <th>Brand</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody *ngIf="products.length > 0; else noProducts">
              <tr *ngFor="let product of products">
                <td>
                  <img [src]="productService.getProductImage(product)" alt="" class="rounded" style="width: 48px; height: 48px; object-fit: cover;">
                </td>
                <td>{{ product.productName }}</td>
                <td>{{ product.brandName }}</td>
                <td>{{ product.price }}</td>
                <td>{{ product.quantity }}</td>
                <td>
                  <span class="badge" [ngClass]="{
                    'bg-success bg-opacity-10 text-success': product.active,
                    'bg-secondary bg-opacity-10 text-secondary': !product.active
                  }">{{ product.active ? 'Active' : 'Inactive' }}</span>
                </td>
              </tr>
            </tbody>
            <ng-template #noProducts>
              <tr>
                <td colspan="6" class="text-center text-muted py-4">No products yet. Add your first product!</td>
              </tr>
            </ng-template>
          </table>
        </div>
      </div>
    </div>
  `
})
export class MerchantProductsComponent implements OnInit {
  products: Product[] = [];
  categories: Category[] = [];
  showAddProductForm = false;
  isSubmitting = false;
  errorMessage: string | null = null;
  successMessage: string | null = null;
  imagePreview: string | null = null;
  productForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    public productService: ProductService
  ) {
    this.productForm = this.fb.group({
      productName: ['', Validators.required],
      categoryId: ['', Validators.required],
      brandName: ['', Validators.required],
      price: [null, [Validators.required, Validators.min(0.01)]],
      quantity: [0, [Validators.required, Validators.min(0)]],
      description: [''],
      active: [true]
    });
  }

  ngOnInit(): void {
    this.loadProducts();
    this.loadCategories();
  }

  loadCategories(): void {
    this.productService.getCategories().subscribe({
      next: (response) => {
        this.categories = response.data || [];
      },
      error: (err) => {
        console.error('Error loading categories:', err);
      }
    });
  }

  loadProducts(): void {
    const currentUser = this.authService.currentUserValue;
    if (currentUser?.uuid) {
      this.productService.getAllProductsByMerchantUuid(currentUser.uuid).subscribe({
        next: (response) => {
          this.products = response.data || [];
        },
        error: (err) => {
          console.error('Error loading products:', err);
        }
      });
    }
  }

  onImageSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    const file = input.files?.[0];
    if (!file) {
      return;
    }

    if (file.size > 2 * 1024 * 1024) {
      this.errorMessage = 'Image must be smaller than 2MB.';
      input.value = '';
      return;
    }

    const reader = new FileReader();
    reader.onload = () => {
      this.imagePreview = reader.result as string;
      this.errorMessage = null;
    };
    reader.readAsDataURL(file);
  }

  onAddProduct(): void {
    if (this.productForm.invalid || !this.imagePreview) {
      return;
    }

    const currentUser = this.authService.currentUserValue;
    if (!currentUser?.uuid) {
      this.errorMessage = 'You must be logged in to add products.';
      return;
    }

    this.isSubmitting = true;
    this.errorMessage = null;
    this.successMessage = null;

    const product: CreateProductRequest = {
      productName: this.productForm.value.productName,
      categoryId: this.productForm.value.categoryId,
      brandName: this.productForm.value.brandName,
      price: this.productForm.value.price,
      quantity: this.productForm.value.quantity,
      description: this.productForm.value.description,
      productImages: [this.imagePreview],
      merchantUuid: currentUser.uuid,
      active: true
    };

    this.productService.createProduct(product).subscribe({
      next: () => {
        this.isSubmitting = false;
        this.successMessage = 'Product added successfully!';
        this.productForm.reset({ active: true, quantity: 0 });
        this.imagePreview = null;
        this.showAddProductForm = false;
        this.loadProducts();
      },
      error: (err) => {
        this.isSubmitting = false;
        this.errorMessage = err.error?.message || 'Failed to add product. Please check all fields and try again.';
      }
    });
  }
}
