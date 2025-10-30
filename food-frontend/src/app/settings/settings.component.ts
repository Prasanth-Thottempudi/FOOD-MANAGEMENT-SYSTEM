import { Component } from '@angular/core';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css'],
})
export class SettingsComponent {
  settings = {
    fullName: 'Karthik Agent',
    email: 'karthik.agent@example.com',
    phone: '+91 9876543210',
    password: '********',
    pushNotifications: true,
    emailNotifications: false,
    orderUpdates: true,
    darkMode: false,
  };

  saveSettings() {
    console.log('Updated Settings:', this.settings);
    alert('Settings saved successfully!');
  }
}
