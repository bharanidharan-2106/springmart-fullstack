import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

export interface UserSession {
  token: string;
  email: string;
  role: string;
  firstName: string;
  lastName: string;
  uuid: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/auth';
  private currentUserSubject = new BehaviorSubject<UserSession | null>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient) {
    const savedUser = localStorage.getItem('springmart_user');
    if (savedUser) {
      try {
        this.currentUserSubject.next(JSON.parse(savedUser));
      } catch (e) {
        localStorage.removeItem('springmart_user');
      }
    }
  }

  public get currentUserValue(): UserSession | null {
    return this.currentUserSubject.value;
  }

  login(credentials: any): Observable<UserSession> {
    return this.http.post<UserSession>(`${this.baseUrl}/login`, credentials).pipe(
      tap(user => {
        localStorage.setItem('springmart_user', JSON.stringify(user));
        this.currentUserSubject.next(user);
      })
    );
  }

  register(userData: any): Observable<UserSession> {
    return this.http.post<UserSession>(`${this.baseUrl}/register`, userData).pipe(
      tap(user => {
        localStorage.setItem('springmart_user', JSON.stringify(user));
        this.currentUserSubject.next(user);
      })
    );
  }

  logout(): void {
    localStorage.removeItem('springmart_user');
    this.currentUserSubject.next(null);
  }

  isAuthenticated(): boolean {
    return this.currentUserValue !== null;
  }
}
