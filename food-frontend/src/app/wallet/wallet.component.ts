import { Component } from '@angular/core';

interface Transaction {
  date: string;
  type: string;
  amount: number;
  status: string;
}

@Component({
  selector: 'app-wallet',
  templateUrl: './wallet.component.html',
  styleUrls: ['./wallet.component.css'],
})
export class WalletComponent {
  balance = 1250.75; // Example balance
  currency = '₹';

  transactions: Transaction[] = [
    { date: '2025-10-23', type: 'Add Money', amount: 500, status: 'Success' },
    { date: '2025-10-22', type: 'Payment', amount: 250, status: 'Success' },
    { date: '2025-10-20', type: 'Refund', amount: 100, status: 'Pending' },
  ];

  addMoneyAmount = 0;

  addMoney() {
    if (this.addMoneyAmount > 0) {
      this.balance += this.addMoneyAmount;
      this.transactions.unshift({
        date: new Date().toISOString().split('T')[0],
        type: 'Add Money',
        amount: this.addMoneyAmount,
        status: 'Success',
      });
      this.addMoneyAmount = 0;
      alert('Money added successfully!');
    }
  }
}
