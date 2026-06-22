import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService, User } from '../../services/user.service';
import { AuthService } from '../../services/auth.service';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, MatIconModule, MatSnackBarModule],
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  profileForm!: FormGroup;
  currentUser: User | null = null;
  activeTab: 'settings' | 'orders' = 'settings';
  isLoading = false;

  mockOrders = [
    { id: '#ORD-10023', date: '2023-10-15', total: 129.99, status: 'Delivered', items: 3 },
    { id: '#ORD-10045', date: '2023-11-02', total: 45.50, status: 'Processing', items: 1 },
    { id: '#ORD-10078', date: '2023-12-10', total: 310.00, status: 'Shipped', items: 4 },
  ];

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private authService: AuthService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.loadUserProfile();
  }

  initForm(): void {
    this.profileForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      phone: [''],
      address: this.fb.group({
        street: [''],
        city: [''],
        state: [''],
        country: [''],
        zipCode: ['']
      })
    });
  }

  loadUserProfile(): void {
    const authUser = this.authService.currentUserValue;
    if (authUser && authUser.uuid) {
      this.isLoading = true;
      this.userService.getUserProfile(authUser.uuid).subscribe({
        next: (user) => {
          this.currentUser = user;
          this.profileForm.patchValue({
            firstName: user.firstName,
            lastName: user.lastName,
            phone: user.phone || '',
            address: user.address || {
              street: '', city: '', state: '', country: '', zipCode: ''
            }
          });
          this.isLoading = false;
        },
        error: (err) => {
          console.error('Error fetching profile', err);
          this.snackBar.open('Failed to load profile.', 'Close', { duration: 3000 });
          this.isLoading = false;
        }
      });
    }
  }

  onSubmit(): void {
    if (this.profileForm.valid && this.currentUser?.uuid) {
      this.isLoading = true;
      this.userService.updateProfile(this.currentUser.uuid, this.profileForm.value).subscribe({
        next: (updatedUser) => {
          this.currentUser = updatedUser;
          this.snackBar.open('Profile updated successfully!', 'Close', { duration: 3000 });
          this.isLoading = false;
          
          const currentAuth = this.authService.currentUserValue;
          if (currentAuth) {
              currentAuth.firstName = updatedUser.firstName;
              currentAuth.lastName = updatedUser.lastName;
              this.authService.updateCurrentUser(currentAuth);
          }
        },
        error: (err) => {
          console.error('Error updating profile', err);
          this.snackBar.open('Failed to update profile.', 'Close', { duration: 3000 });
          this.isLoading = false;
        }
      });
    }
  }
}
