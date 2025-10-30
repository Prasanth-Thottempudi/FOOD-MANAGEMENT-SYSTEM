import { HttpClient } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonServicesService } from '../services/common-services.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-add-service',
  templateUrl: './add-service.component.html',
  styleUrls: ['./add-service.component.css'],
})
export class AddServiceComponent implements OnInit {
  services: any[];

  serviceHeader = 'services';

  isFormVisible;

  newServiceForm: FormGroup;

  selectedImage: string | ArrayBuffer | null = null;

  onFileSelected(event: any): void {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e) => {
        this.selectedImage = e.target?.result;
      };
      reader.readAsDataURL(file);
    }
  }

  constructor(
    private fb: FormBuilder,
    private router: Router,
    // private dialogRef: MatDialogRef<ServiceDialogComponent>,

    private http: CommonServicesService
  ) {
    this.newServiceForm = this.fb.group({
      servicename: [''],
      servicedescription: [''],
      serviceImageUrl: [''],
    });
  }
  ngOnInit() {
    this.http.getAllServices().subscribe((response) => {
      this.services = response;
      console.log(response);
    });
  }

  toggleForm() {
    this.isFormVisible = !this.isFormVisible;
  }

  onServiceSubmit() {}

  closeForm() {
    console.log('close form called');
    this.isFormVisible = false;
    this.selectedImage = null;
  }
}
