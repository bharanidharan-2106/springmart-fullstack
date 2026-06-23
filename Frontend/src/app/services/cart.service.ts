import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject, tap } from 'rxjs';

export interface CartItem {
  id: number;
  productId: number;
  productName: string;
  productImage: string;
  sku: string;
  price: number;
  quantity: number;
  lineTotal: number;
}

export interface CartResponse {
  cartId: number;
  userId: number;
  totalItems: number;
  totalQuantity: number;
  totalAmount: number;
  items: CartItem[];
}

export interface CartSummaryResponse {
  totalItems: number;
  totalQuantity: number;
  totalAmount: number;
}

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private apiUrl = 'http://localhost:8080/api/cart';
  private cartSummarySubject = new BehaviorSubject<CartSummaryResponse>({ totalItems: 0, totalQuantity: 0, totalAmount: 0 });
  
  cartSummary$ = this.cartSummarySubject.asObservable();

  constructor(private http: HttpClient) { }

  getCart(): Observable<CartResponse> {
    return this.http.get<CartResponse>(this.apiUrl).pipe(
      tap(cart => this.updateSummary(cart))
    );
  }

  getSummary(): Observable<CartSummaryResponse> {
    return this.http.get<CartSummaryResponse>(`${this.apiUrl}/summary`).pipe(
      tap(summary => this.cartSummarySubject.next(summary))
    );
  }

  addToCart(productId: number, quantity: number = 1): Observable<CartResponse> {
    return this.http.post<CartResponse>(`${this.apiUrl}/items`, { productId, quantity }).pipe(
      tap(cart => this.updateSummary(cart))
    );
  }

  updateQuantity(cartItemId: number, quantity: number): Observable<CartResponse> {
    return this.http.put<CartResponse>(`${this.apiUrl}/items/${cartItemId}`, { quantity }).pipe(
      tap(cart => this.updateSummary(cart))
    );
  }

  removeItem(cartItemId: number): Observable<CartResponse> {
    return this.http.delete<CartResponse>(`${this.apiUrl}/items/${cartItemId}`).pipe(
      tap(cart => this.updateSummary(cart))
    );
  }

  clearCart(): Observable<void> {
    return this.http.delete<void>(this.apiUrl).pipe(
      tap(() => this.cartSummarySubject.next({ totalItems: 0, totalQuantity: 0, totalAmount: 0 }))
    );
  }
  
  private updateSummary(cart: CartResponse): void {
    this.cartSummarySubject.next({
      totalItems: cart.totalItems,
      totalQuantity: cart.totalQuantity,
      totalAmount: cart.totalAmount
    });
  }
}
