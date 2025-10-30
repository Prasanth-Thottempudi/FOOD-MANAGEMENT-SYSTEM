import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

interface CartItem {
  id: number;
  name: string;
  description: string;
  image: string;
  price: number;
  quantity: number;
}

interface SuggestedItem {
  id: number;
  name: string;
  image: string;
  price: number;
}

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
})
export class CartComponent implements OnInit {
  cartItems: CartItem[] = [];
  suggestedItems: SuggestedItem[] = [];
  couponCode: string = '';

  bill = {
    itemTotal: 0,
    handlingFee: 10,
    smartCartFee: 5,
    deliveryTip: 20,
    deliveryPartnerFee: 15,
    gst: 0,
    total: 0,
  };

  constructor(private router: Router) {}

  ngOnInit(): void {
    // Mock data for now — replace with API call later
    this.cartItems = [
      {
        id: 1,
        name: 'Margherita Pizza',
        description: 'Classic cheese pizza with tomato sauce',
        image: '../assets/pizza.png',
        price: 299,
        quantity: 1,
      },
      {
        id: 2,
        name: 'Cheese Burger',
        description: 'Loaded with cheese and fresh veggies',
        image: '../assets/pizza.png',
        price: 199,
        quantity: 2,
      },
      {
        id: 3,
        name: 'French Fries',
        description: 'Crispy golden potato fries',
        image: '../assets/pizza.png',
        price: 99,
        quantity: 1,
      },
    ];

    this.suggestedItems = [
      {
        id: 101,
        name: 'Cold Coffee',
        image: '../assets/pizza.png',
        price: 120,
      },
      {
        id: 102,
        name: 'Chocolate Shake',
        image: '../assets/pizza.png',
        price: 150,
      },
      {
        id: 103,
        name: 'Garlic Bread',
        image: '../assets/pizza.png',
        price: 89,
      },
      { id: 104, name: 'Brownie', image: '../assets/pizza.png', price: 160 },
    ];

    this.calculateBill();
  }

  increaseQuantity(item: CartItem): void {
    item.quantity++;
    this.calculateBill();
  }

  decreaseQuantity(item: CartItem): void {
    if (item.quantity > 1) {
      item.quantity--;
      this.calculateBill();
    }
  }

  removeItem(item: CartItem): void {
    this.cartItems = this.cartItems.filter((i) => i.id !== item.id);
    this.calculateBill();
  }

  addToCart(item: SuggestedItem): void {
    const existing = this.cartItems.find((c) => c.name === item.name);
    if (existing) {
      existing.quantity++;
    } else {
      this.cartItems.push({
        id: item.id,
        name: item.name,
        description: 'Delicious and freshly made',
        image: item.image,
        price: item.price,
        quantity: 1,
      });
    }
    this.calculateBill();
  }

  applyCoupon(): void {
    if (this.couponCode.trim().toLowerCase() === 'food20') {
      this.bill.itemTotal = this.bill.itemTotal * 0.8; // 20% discount
      this.calculateBill(true);
      alert('Coupon applied successfully! You got 20% off 🎉');
    } else {
      alert('Invalid coupon code ❌');
    }
  }

  calculateBill(fromCoupon: boolean = false): void {
    let total = this.cartItems.reduce(
      (acc, item) => acc + item.price * item.quantity,
      0
    );
    this.bill.itemTotal = total;

    // Calculate GST (5%)
    this.bill.gst = Math.round(total * 0.05);

    // Final total
    this.bill.total =
      this.bill.itemTotal +
      this.bill.handlingFee +
      this.bill.smartCartFee +
      this.bill.deliveryTip +
      this.bill.deliveryPartnerFee +
      this.bill.gst;
  }

  proceedToPayment(): void {
    this.router.navigate(['/user-dashboard/payment']);
  }
}
