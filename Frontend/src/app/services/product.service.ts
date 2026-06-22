import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Product {
  id?: string;
  merchantUuid?: string;
  productName: string;
  sku?: string;
  slug?: string;
  shortDescription?: string;
  description?: string;
  categoryId: string;
  categoryName?: string;
  brandId?: string;
  brandName?: string;
  price: number;
  discountedPrice?: number;
  quantity: number;
  productImages?: string[];
  specifications?: any;
  averageRating?: number;
  reviewCount?: number;
  status?: string;
  active?: boolean;
  createdAt?: string;
  updatedAt?: string;
}

export interface CreateProductRequest {
  merchantUuid?: string;
  productName: string;
  categoryId: string;
  brandName: string;
  price: number;
  quantity: number;
  description?: string;
  productImages: string[];
  active?: boolean;
}

export interface ApiResponse<T> {
  message: string;
  data: T;
}

export interface Category {
  id: string;
  name: string;
  description?: string;
  active?: boolean;
}

export interface PagedResponse<T> {
  content: T[];
  pageNo: number;
  pageSize: number;
  totalElements: number;
  totalPages: number;
  last: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private baseUrl = 'http://localhost:8080/api/products';
  private categoriesUrl = 'http://localhost:8080/api/categories';

  constructor(private http: HttpClient) { }

  createProduct(product: CreateProductRequest): Observable<ApiResponse<Product>> {
    return this.http.post<ApiResponse<Product>>(this.baseUrl, product);
  }

  updateProduct(id: string, product: CreateProductRequest): Observable<ApiResponse<Product>> {
    return this.http.put<ApiResponse<Product>>(`${this.baseUrl}/${id}`, product);
  }

  deleteProduct(id: string): Observable<ApiResponse<void>> {
    return this.http.delete<ApiResponse<void>>(`${this.baseUrl}/${id}`);
  }

  getProductById(id: string): Observable<ApiResponse<Product>> {
    return this.http.get<ApiResponse<Product>>(`${this.baseUrl}/${id}`);
  }

  getProductsByMerchantUuid(merchantUuid: string, page: number = 0, size: number = 10): Observable<ApiResponse<PagedResponse<Product>>> {
    return this.http.get<ApiResponse<PagedResponse<Product>>>(`${this.baseUrl}/merchant/${merchantUuid}?page=${page}&size=${size}`);
  }

  getAllProductsByMerchantUuid(merchantUuid: string): Observable<ApiResponse<Product[]>> {
    return this.http.get<ApiResponse<Product[]>>(`${this.baseUrl}/merchant/${merchantUuid}/all`);
  }

  getAllProducts(page: number = 0, size: number = 12): Observable<ApiResponse<PagedResponse<Product>>> {
    return this.http.get<ApiResponse<PagedResponse<Product>>>(`${this.baseUrl}?page=${page}&size=${size}`);
  }

  getProductsByCategory(categoryId: string, page: number = 0, size: number = 12): Observable<ApiResponse<PagedResponse<Product>>> {
    return this.http.get<ApiResponse<PagedResponse<Product>>>(`${this.baseUrl}/category/${categoryId}?page=${page}&size=${size}`);
  }

  searchProducts(keyword: string, page: number = 0, size: number = 12): Observable<ApiResponse<PagedResponse<Product>>> {
    return this.http.get<ApiResponse<PagedResponse<Product>>>(`${this.baseUrl}/search?keyword=${encodeURIComponent(keyword)}&page=${page}&size=${size}`);
  }

  getCategories(): Observable<ApiResponse<Category[]>> {
    return this.http.get<ApiResponse<Category[]>>(this.categoriesUrl);
  }

  getProductImage(product: Product): string {
    if (product.productImages && product.productImages.length > 0) {
      return product.productImages[0];
    }
    return 'https://placehold.co/400x400/F5F5F5/1A1A1A?text=No+Image';
  }
}
