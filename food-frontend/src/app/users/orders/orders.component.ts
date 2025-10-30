import { Component, OnInit } from '@angular/core';

interface Order {
  productName: string;
  productImage: string;
  quantity: number;
  deliveryRating: number; // 1-5
  foodRating: number; // 1-5
  orderedDate: Date;
}

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css'],
})
export class OrdersComponent implements OnInit {
  orders: Order[] = [];

  ngOnInit(): void {
    // Sample Data (replace with API data)
    this.orders = [
      {
        productName: 'Cheese Pizza',
        productImage: '../../../assets/pizza.png',
        quantity: 2,
        deliveryRating: 4,
        foodRating: 5,
        orderedDate: new Date('2025-10-18T12:30:00'),
      },
      {
        productName: 'Veg Burger',
        productImage: '../../../assets/pizza.png',
        quantity: 1,
        deliveryRating: 5,
        foodRating: 4,
        orderedDate: new Date('2025-10-20T14:45:00'),
      },
      {
        productName: 'Pasta',
        productImage: '../../../assets/pizza.png',
        quantity: 3,
        deliveryRating: 3,
        foodRating: 5,
        orderedDate: new Date('2025-10-21T19:00:00'),
      },
      {
        productName: 'Pasta',
        productImage: '../../../assets/pizza.png',
        quantity: 3,
        deliveryRating: 3,
        foodRating: 5,
        orderedDate: new Date('2025-10-21T19:00:00'),
      },
      {
        productName: 'Pasta',
        productImage: '../../../assets/pizza.png',
        quantity: 3,
        deliveryRating: 3,
        foodRating: 5,
        orderedDate: new Date('2025-10-21T19:00:00'),
      },
    ];
  }

  // Helper to generate stars for rating
  getStars(rating: number): number[] {
    return Array(rating).fill(0);
  }
}
