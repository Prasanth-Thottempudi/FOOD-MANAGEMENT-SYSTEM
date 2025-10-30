import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-business-patner-registration-form',
  templateUrl: './business-patner-registration-form.component.html',
  styleUrls: ['./business-patner-registration-form.component.css'],
})
export class BusinessPatnerRegistrationFormComponent {
  constructor(private router: Router) {}

  currentStep = 1;

  // Step Images
  stepImages = [
    '../../assets/registration/patnerregistratio.png', // Step 1
    '../../assets/registration/patnerregistratio.png', // Step 2
    '../../assets/registration/patnerregistratio.png', // Step 3
    '../../assets/registration/patnerregistratio.png', // Step 4
  ];

  get currentStepImage(): string {
    return this.stepImages[this.currentStep - 1];
  }

  // Step 1: Personal Info
  fullName = '';
  email = '';
  phoneNumber = '';
  dob = '';
  gender = '';
  nationality = '';
  idProofFile: File | null = null;

  // Step 2: Basic Business Info
  serviceName = '';
  mode: boolean | null = null;
  businessName = '';
  businessEmail = '';
  businessMobileNumber = '';

  // Step 3: Business Documents
  businessLogoFile: File | null = null;
  businessRegion = '';
  businessLicenseFile: File | null = null;

  // Step 4: Additional Info
  language = '';
  emergencyContact = '';
  businessDescription = '';

  // Navigation
  nextStep() {
    if (this.currentStep < 4) this.currentStep++;
  }

  prevStep() {
    if (this.currentStep > 1) this.currentStep--;
  }

  cancel() {
    this.router.navigate(['/']);
  }

  // File Selection
  onFileSelected(event: any, type: string) {
    const file = event.target.files[0];
    if (type === 'idProof') this.idProofFile = file;
    else if (type === 'businessLogo') this.businessLogoFile = file;
    else if (type === 'businessLicense') this.businessLicenseFile = file;
  }

  // Submit
  onSubmit() {
    const registrationData = {
      fullName: this.fullName,
      email: this.email,
      phoneNumber: this.phoneNumber,
      dob: this.dob,
      gender: this.gender,
      nationality: this.nationality,
      idProofFile: this.idProofFile,
      serviceName: this.serviceName,
      mode: this.mode,
      businessName: this.businessName,
      businessEmail: this.businessEmail,
      businessMobileNumber: this.businessMobileNumber,
      businessLogoFile: this.businessLogoFile,
      businessRegion: this.businessRegion,
      businessLicenseFile: this.businessLicenseFile,
      language: this.language,
      emergencyContact: this.emergencyContact,
      businessDescription: this.businessDescription,
    };
    console.log('Registration submitted:', registrationData);
    alert('Registration submitted! Check console for data.');
  }
}
