import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-features',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './features.component.html',
  styleUrl: './features.component.scss'
})
export class FeaturesComponent {
  features = [
    { icon: '🚚', title: 'Fast Delivery', text: 'Quick and reliable delivery services.' },
    { icon: '🔒', title: 'Secure Payments', text: '100% secure payment transactions.' },
    { icon: '💰', title: 'Best Prices', text: 'Competitive pricing on all products.' },
    { icon: '🎧', title: '24/7 Support', text: 'Dedicated customer support anytime.' }
  ];
}
