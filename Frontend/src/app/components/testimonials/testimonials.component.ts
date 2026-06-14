import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-testimonials',
  standalone: true,
  imports: [CommonModule, MatIconModule],
  templateUrl: './testimonials.component.html',
  styleUrl: './testimonials.component.scss'
})
export class TestimonialsComponent {
  testimonials = [
    { name: 'Sarah Johnson', text: 'SpringMart offers the best shopping experience. Fast delivery and excellent quality products!', rating: 5, image: 'https://placehold.co/100x100/F5F5F5/1A1A1A?text=SJ' },
    { name: 'Michael Chen', text: 'I love the minimalist design and how easy it is to find what I need. Customer service is top-notch.', rating: 5, image: 'https://placehold.co/100x100/F5F5F5/1A1A1A?text=MC' },
    { name: 'Emily Davis', text: 'Great prices and secure payments. I have been shopping here for months and never had an issue.', rating: 4, image: 'https://placehold.co/100x100/F5F5F5/1A1A1A?text=ED' }
  ];

  getStars(rating: number): number[] {
    return Array(rating).fill(0);
  }
}
