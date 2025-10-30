import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonServicesService } from '../services/common-services.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-admin-data-table',
  templateUrl: './admin-data-table.component.html',
  styleUrls: ['./admin-data-table.component.css'],
})
export class AdminDataTableComponent implements OnInit {
  approvalRequests: any[] = [];

  constructor(
    private route: Router,
    private fb: FormBuilder,
    private http: CommonServicesService,
    private snackBar: MatSnackBar, // Inject MatSnackBar
  ) {}
  ngOnInit(): void {
    this.http.adminServiceApprovalRequests().subscribe((response) => {
      console.log(response);
      this.approvalRequests = response;
    });
  }

  approveRequest(request: any) {
    const approvalRequest = {
      id: request.id,
      approval: true,
    };
    console.log('approved request button invoked');
    this.http.businessApprovalRequest(approvalRequest).subscribe(
      (response) => {
        console.log('Approval response:', response);
        // Handle success (e.g., show notification, update UI, etc.)
        this.snackBar.open('Successfully approved!', 'Close', {
          duration: 3000, // Duration in milliseconds
          panelClass: ['success-snackbar'], // Custom class for styling
        });
      },
      (error) => {
        console.error('Error approving request:', error);
        // Handle error (e.g., show error message, retry logic, etc.)
      }
    );
  }
}
