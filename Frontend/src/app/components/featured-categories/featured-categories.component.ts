import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-featured-categories',
  standalone: true,
  imports: [CommonModule, MatIconModule],
  templateUrl: './featured-categories.component.html',
  styleUrl: './featured-categories.component.scss'
})
export class FeaturedCategoriesComponent {
  categories = [
    { name: 'Electronics', icon: 'devices' },
    { name: 'Fashion', icon: 'checkroom' },
    { name: 'Home Appliances', icon: 'kitchen' },
    { name: 'Books', icon: 'menu_book' },
    { name: 'Sports', icon: 'sports_basketball' },
    { name: 'Groceries', icon: 'local_grocery_store' }
  ];
}
