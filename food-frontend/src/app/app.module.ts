import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { RegistrationComponent } from './registration/registration.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { LoginComponent } from './login/login.component';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BusinessPatnerRegistrationFormComponent } from './business-patner-registration-form/business-patner-registration-form.component';
import { MatSelectModule } from '@angular/material/select';
import { MatStepperModule } from '@angular/material/stepper';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatIconModule } from '@angular/material/icon';
import { HttpClientModule } from '@angular/common/http';
import { SideNavComponent } from './side-nav/side-nav.component';
import { ProfileComponent } from './profile/profile.component';
import { AdminHeaderComponent } from './admin-header/admin-header.component';
import { AdminDataTableComponent } from './admin-data-table/admin-data-table.component';
import { AddServiceComponent } from './add-service/add-service.component';
import { MatDialogModule } from '@angular/material/dialog';
import { ServiceDialogComponent } from './service-dialog/service-dialog.component';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { ToastrModule } from 'ngx-toastr';
import { SellerDashboardComponent } from './seller-dashboard/seller-dashboard.component';
import { ReciepesComponent } from './seller/reciepes/reciepes.component';
import { GoogleMapsModule } from '@angular/google-maps';
import { AgentDashboardComponent } from './deliveragent/agentdashboard/agentdashboard.component';
import { AgenthomeComponent } from './deliveragent/agenthome/agenthome.component';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: '', component: DashboardComponent },
  { path: 'login', component: LoginComponent },
  { path: 'registration', component: RegistrationComponent },
  {
    path: 'user-dashboard',
    loadChildren: () => import('./users/user.module').then((m) => m.UserModule),
  },
  // Add admin, seller, and agent modules here later if you modularize them
];

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    RegistrationComponent,
    HeaderComponent,
    FooterComponent,
    LoginComponent,
    AdminDashboardComponent,
    BusinessPatnerRegistrationFormComponent,
    SideNavComponent,
    ProfileComponent,
    AdminHeaderComponent,
    AdminDataTableComponent,
    AddServiceComponent,
    ServiceDialogComponent,
    SellerDashboardComponent,
    ReciepesComponent,
    AgentDashboardComponent,
    AgenthomeComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule.forRoot(routes),
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    ToastrModule.forRoot(),
    MatButtonModule,
    MatInputModule,
    MatCardModule,
    MatFormFieldModule,
    MatSelectModule,
    MatStepperModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatIconModule,
    MatDialogModule,
    MatSnackBarModule,
    GoogleMapsModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
