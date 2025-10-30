import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { CommonServicesService } from '../services/common-services.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  password: any;
  username: any;

  closeForm() {}

  loginForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private serv: CommonServicesService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      username: [''],
      password: [''],
    });
  }

  onSubmit() {
    console.log('login form ');
  }

  onCancel() {
    this.router.navigate(['/']);
  }
  onLogin() {
    this.router.navigate(['/user-dashboard/food']);
  }
}
