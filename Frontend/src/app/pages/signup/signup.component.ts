import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink, MatIconModule],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.scss'
})
export class SignupComponent {
  signupForm: FormGroup;
  hidePassword = true;
  isLoading = false;
  errorMessage: string | null = null;
  successMessage: string | null = null;
  roles = [
    { value: 'Customer', label: 'Customer', icon: 'person', desc: 'Shop and track orders' },
    { value: 'Merchant', label: 'Merchant', icon: 'storefront', desc: 'Sell and manage products' }
  ];

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.signupForm = this.fb.group({
      firstName: ['', [Validators.required, Validators.maxLength(50)]],
      lastName: ['', [Validators.required, Validators.maxLength(50)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      role: ['Customer', [Validators.required]]
    });
  }

  selectRole(roleValue: string): void {
    this.signupForm.patchValue({ role: roleValue });
  }

  onSubmit(): void {
    if (this.signupForm.invalid) {
      this.signupForm.markAllAsTouched();
      return;
    }

    this.isLoading = true;
    this.errorMessage = null;
    this.successMessage = null;

    this.authService.register(this.signupForm.value).subscribe({
      next: (user) => {
        this.isLoading = false;
        const role = user?.role ? user.role.toUpperCase() : '';

        if (role.includes('MERCHANT') && user.status === 'PENDING') {
          this.successMessage = 'Registration successful! Your merchant account is pending admin approval. You can sign in once approved.';
          this.signupForm.reset({ role: 'Customer' });
          return;
        }

        if (role.includes('ADMIN')) {
          this.router.navigate(['/admin/dashboard']);
        } else if (role.includes('MERCHANT')) {
          this.router.navigate(['/merchant/dashboard']);
        } else {
          this.router.navigate(['/']);
        }
      },
      error: (err) => {
        this.isLoading = false;
        this.errorMessage = err.error?.message || 'An error occurred during registration. Please try again.';
      }
    });
  }
}
