import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent {
  constructor(private router: Router) {}

  Email: string = 'support@eshop.com';
  categories = [
    { name: 'Pizza', image: '../assets/biryani.png' },
    { name: 'Burger', image: '../assets/cakes.png' },
    { name: 'Pasta', image: '../assets/pizza.png' },
    { name: 'Dessert', image: '../assets/noodles.png' },
    { name: 'Drinks', image: '../assets/rolls.png' },
  ];

  featuredItems = [
    {
      name: 'Pizza Margherita',
      description: 'Classic cheese & tomato',
      price: 299,
      image: '../assets/pizza.png',
    },
    {
      name: 'Veg Burger',
      description: 'Grilled patty with fresh veggies',
      price: 149,
      image: '../assets/pizza.png',
    },
    {
      name: 'Pasta Alfredo',
      description: 'Creamy white sauce pasta',
      price: 199,
      image: '../assets/pizza.png',
    },
  ];

  recommendedItems = [
    {
      name: 'French Fries',
      description: 'Crispy golden fries',
      price: 99,
      image: '../assets/pizza.png',
    },
    {
      name: 'Chocolate Shake',
      description: 'Rich & creamy',
      price: 129,
      image: '../assets/pizza.png',
    },
  ];

  restaurants = [
    {
      name: 'Pizza Palace',
      cuisine: 'Italian',
      image: '../assets/pizza.png',
    },
    {
      name: 'Burger Hub',
      cuisine: 'Fast Food',
      image: '../assets/pizza.png',
    },
    {
      name: 'Pasta Corner',
      cuisine: 'Italian',
      image: '../assets/pizza.png',
    },
  ];

  onLogin(): void {
    this.router.navigate(['/agent-dashboard']);
  }
}
