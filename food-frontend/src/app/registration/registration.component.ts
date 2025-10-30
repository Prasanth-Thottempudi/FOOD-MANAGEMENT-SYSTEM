import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserRequest } from '../models/user-request.model';
import { ApiEndpoints } from './../ApiEndpoints';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css'],
})
export class RegistrationComponent {
  constructor(private http: HttpClient, private router: Router) {}

  activeTab = 0;
  addAddress = false;

  selectedFile: File | null = null;
  selectedFileName: string = '';

  apiEndPoint = ApiEndpoints;

  user: UserRequest = {
    name: '',
    email: '',
    password: '',
    mobileNumber: '',
    profileImage: null,
    latitude: null,
    longitude: null,
    addressLine1: '',
    addressLine2: '',
    city: '',
    state: '',
    postalCode: '',
    country: '',
  };

  // Handle file selection
  onFileChange(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      this.selectedFileName = file.name;
      this.user.profileImage = file;
    } else {
      this.selectedFileName = '';
      this.user.profileImage = null;
      this.addAddress = false;
    }
  }

  // Navigate to home page (Cancel)
  onCancel() {
    this.router.navigate(['/']);
  }

  // Basic client-side validation
  private isFormValid(): boolean {
    const emailPattern = /^[a-zA-Z0-9._%+-]+@gmail\.com$/;
    const passwordPattern =
      /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?#&])[A-Za-z\d@$!%*?#&]{8,}$/;
    const phonePattern = /^[6-9]\d{9}$/;

    return (
      this.user.name.trim() !== '' &&
      emailPattern.test(this.user.email) &&
      passwordPattern.test(this.user.password) &&
      phonePattern.test(this.user.mobileNumber) &&
      this.user.profileImage !== null
    );
  }

  // Reset form after submission
  private resetForm() {
    this.user = {
      name: '',
      email: '',
      password: '',
      mobileNumber: '',
      profileImage: null,
      latitude: null,
      longitude: null,
      addressLine1: '',
      addressLine2: '',
      city: '',
      state: '',
      postalCode: '',
      country: '',
    };
    this.selectedFile = null;
    this.selectedFileName = '';
    this.addAddress = false;
    this.activeTab = 0;
  }

  // Submit form
  onSubmit() {
    if (!this.isFormValid()) {
      alert(
        'Please fill all required fields correctly and select a profile image.'
      );
      return;
    }

    const formData = new FormData();

    // Append the file under key "file"
    if (this.user.profileImage) {
      formData.append('file', this.user.profileImage);
    }

    // Convert user data to JSON string
    const userJson = JSON.stringify({
      name: this.user.name,
      email: this.user.email,
      password: this.user.password,
      mobileNumber: this.user.mobileNumber,
      addressLine1: this.addAddress ? this.user.addressLine1 : '',
      addressLine2: this.addAddress ? this.user.addressLine2 : '',
      city: this.addAddress ? this.user.city : '',
      state: this.addAddress ? this.user.state : '',
      postalCode: this.addAddress ? this.user.postalCode : '',
      country: this.addAddress ? this.user.country : '',
    });

    // Append JSON string under key "user"
    formData.append('user', userJson);

    // Send POST request to backend
    this.http.post(ApiEndpoints.USER.USER_REGISTRATIOIN, formData).subscribe({
      next: (res) => {
        console.log('API response:', res);
        Swal.fire({
          title: '🍔 Registration Successful!',
          html: `
    <p style="font-size:16px; margin:0;">Welcome to Foodies Heaven!</p>
    <p style="font-size:14px; color:#555; margin-top:5px;">Your account has been created successfully.</p>
    <div style="font-size:30px; margin-top:10px;">🎉🥗🍕🍰</div>
  `,
          showConfirmButton: true,
          confirmButtonText: 'Start Exploring 🍽️',
          confirmButtonColor: '#ff6f00',
          background: '#fffaf0', // soft, food-themed background
          color: '#4caf50', // green for success
          backdrop: 'rgba(0,0,0,0.4)',
          width: 400,
          padding: '1.5rem',
          showCloseButton: true,
        });

        // this.resetForm();
      },
      error: (err) => {
        console.error('API error:', err);
        Swal.fire({
          icon: 'error',
          title: 'Registration Failed',
          text: 'Something went wrong. Please try again.',
          showConfirmButton: true,
        });
      },
    });
  }
}
