import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { ProductService, Category } from '../../services/product.service';

interface DisplayCategory extends Category {
  icon: string;
}

@Component({
  selector: 'app-featured-categories',
  standalone: true,
  imports: [CommonModule, RouterLink, MatIconModule],
  templateUrl: './featured-categories.component.html',
  styleUrl: './featured-categories.component.scss'
})
export class FeaturedCategoriesComponent implements OnInit {
  categories: DisplayCategory[] = [];

  private readonly iconMap: Record<string, string> = {
    'Electronics': 'devices',
    'Fashion': 'checkroom',
    'Home Appliances': 'kitchen',
    'Books': 'menu_book',
    'Sports': 'sports_basketball',
    'Groceries': 'local_grocery_store'
  };

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.productService.getCategories().subscribe({
      next: (response) => {
        this.categories = (response.data || []).map(category => ({
          ...category,
          icon: this.iconMap[category.name] || 'category'
        }));
      },
      error: (err) => {
        console.error('Error loading categories:', err);
      }
    });
  }
}
