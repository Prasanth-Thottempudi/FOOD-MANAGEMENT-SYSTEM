import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
})
export class ProfileComponent implements OnInit {
  profileData = {
    fullName: 'Karthik Agent',
    role: 'Delivery Agent',
    email: 'karthik.agent@example.com',
    phone: '+91 9876543210',
    dateOfBirth: '1998-07-14',
    gender: 'Male',
    address: '12, MG Road, Bangalore, Karnataka',
    totalOrders: 128,
    rating: 4.6,
  };

  ngOnInit(): void {}
}
