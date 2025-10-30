import { Component } from '@angular/core';
import { Route, Router } from '@angular/router';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css'],
})
export class RegistrationComponent {
  constructor(private router: Router) {}

  currentStep = 1;

  // Form fields
  fullName = '';
  email = '';
  phoneNumber = '';
  dob = '';
  gender = '';
  nationality = '';
  serviceName = '';
  businessDetails = '';
  language = '';
  username = '';
  password = '';
  confirmPassword = '';

  nextStep() {
    if (this.currentStep < 3) this.currentStep++;
    this.updateStepDisplay();
  }

  prevStep() {
    if (this.currentStep > 1) this.currentStep--;
    this.updateStepDisplay();
  }

  updateStepDisplay() {
    const steps = document.querySelectorAll('.step-content');
    steps.forEach((step, index) => {
      step.classList.toggle('active', index === this.currentStep - 1);
    });
  }

  onSubmit() {
    // Registration logic here
    console.log('Registration submitted', {
      fullName: this.fullName,
      email: this.email,
      phoneNumber: this.phoneNumber,
      username: this.username,
    });
  }

  cancel() {
    this.router.navigate(['/']);
  }
}
