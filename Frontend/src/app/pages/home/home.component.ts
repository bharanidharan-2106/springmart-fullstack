import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeroComponent } from '../../components/hero/hero.component';
import { FeaturedCategoriesComponent } from '../../components/featured-categories/featured-categories.component';
import { TrendingProductsComponent } from '../../components/trending-products/trending-products.component';
import { PromotionalBannerComponent } from '../../components/promotional-banner/promotional-banner.component';
import { FeaturesComponent } from '../../components/features/features.component';
import { TestimonialsComponent } from '../../components/testimonials/testimonials.component';
import { NewsletterComponent } from '../../components/newsletter/newsletter.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CommonModule,
    HeroComponent,
    FeaturedCategoriesComponent,
    TrendingProductsComponent,
    PromotionalBannerComponent,
    FeaturesComponent,
    TestimonialsComponent,
    NewsletterComponent
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {}
