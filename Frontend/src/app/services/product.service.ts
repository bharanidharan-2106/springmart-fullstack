import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Product {
  id: string;
  merchantUuid?: string;
  productName: string;
  sku: string;
  slug?: string;
  shortDescription?: string;
  description?: string;
  categoryId: string;
  brandId: string;
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

export interface Brand {
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

  constructor(private http: HttpClient) { }

  createProduct(product: Product): Observable<ApiResponse<Product>> {
    return this.http.post<ApiResponse<Product>>(this.baseUrl, product);
  }

  updateProduct(id: string, product: Product): Observable<ApiResponse<Product>> {
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

  getCategories(): Observable<ApiResponse<Category[]>> {
    return this.http.get<ApiResponse<Category[]>>('http://localhost:8080/api/categories');
  }

  getBrands(): Observable<ApiResponse<Brand[]>> {
    return this.http.get<ApiResponse<Brand[]>>('http://localhost:8080/api/brands');
  }
}
