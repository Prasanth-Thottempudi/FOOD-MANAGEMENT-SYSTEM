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
import { Route, RouterModule, Routes } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BusinessPatnerRegistrationFormComponent } from './business-patner-registration-form/business-patner-registration-form.component';
import { MatOption } from '@angular/material/core';
import { MatLineModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { MatStepperModule } from '@angular/material/stepper';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatIcon, MatIconModule } from '@angular/material/icon';
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
import { UserDashboardComponent } from './users/user-dashboard/user-dashboard.component';
import { FoodComponent } from './users/food/food.component';
import { WishlistComponent } from './users/wishlist/wishlist.component';
import { CartComponent } from './users/cart/cart.component';
import { PaymentComponent } from './users/payment/payment.component';
import { OrdersComponent } from './users/orders/orders.component';
import { TrackingComponent } from './users/tracking/tracking.component';
import { GoogleMapsModule } from '@angular/google-maps';
import { SettingsComponent } from './settings/settings.component';
import Swiper from 'swiper';
import { CustomersupportComponent } from './customersupport/customersupport.component';

import { LivechatComponent } from './livechat/livechat.component';
import { WalletComponent } from './wallet/wallet.component';
import { SmartOrderAssistantComponent } from './smart-order-assistant/smart-order-assistant.component';
import { AgentDashboardComponent } from './deliveragent/agentdashboard/agentdashboard.component';
import { AgenthomeComponent } from './deliveragent/agenthome/agenthome.component';

const routes: Routes = [
  {
    path: '',
    component: DashboardComponent,
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'business-partner-registration',
    component: BusinessPatnerRegistrationFormComponent,
  },
  {
    path: 'registration',
    component: RegistrationComponent,
  },

  {
    path: 'admin-dashboard',
    component: AdminDashboardComponent,
    children: [
      {
        path: 'profile',
        component: ProfileComponent,
      },
      {
        path: 'data-table',
        component: AdminDataTableComponent,
      },
      {
        path: 'services',
        component: AddServiceComponent,
      },
    ],
  },
  {
    path: 'seller-dashboard',
    component: SellerDashboardComponent,
    children: [
      {
        path: 'reciepe',
        component: ReciepesComponent,
      },
      {
        path: 'orders',
        component: OrdersComponent,
      },
      {
        path: 'profile',
        component: ProfileComponent,
      },
      {
        path: 'settings',
        component: SettingsComponent,
      },
    ],
  },
  {
    path: 'agent-dashboard',
    component: AgentDashboardComponent,
    children: [
      {
        path: 'agent-home',
        component: AgenthomeComponent,
      },
    ],
  },
  {
    path: 'user-dashboard',
    component: UserDashboardComponent,
    children: [
      {
        path: 'food',
        component: FoodComponent,
      },
      {
        path: 'cart',
        component: CartComponent,
      },
      {
        path: 'orders',
        component: OrdersComponent,
      },
      {
        path: 'profile',
        component: ProfileComponent,
      },
      {
        path: 'payment',
        component: PaymentComponent,
      },
      {
        path: 'tracking',
        component: TrackingComponent,
      },
      {
        path: 'customer-support',
        component: CustomersupportComponent,
      },
      {
        path: 'live-chat',
        component: LivechatComponent,
      },
      {
        path: 'wallet',
        component: WalletComponent,
      },
      {
        path: 'smart-order-assistant',
        component: SmartOrderAssistantComponent,
      },
      {
        path: 'settings',
        component: SettingsComponent,
      },
    ],
  },
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
    OrdersComponent,
    UserDashboardComponent,
    FoodComponent,
    WishlistComponent,
    CartComponent,
    PaymentComponent,
    TrackingComponent,
    SettingsComponent,
    CustomersupportComponent,
    LivechatComponent,
    WalletComponent,
    SmartOrderAssistantComponent,
    AgentDashboardComponent,
    AgenthomeComponent,
  ],
  imports: [
    BrowserModule,
    ToastrModule,
    AppRoutingModule,
    MatButtonModule,
    MatInputModule,
    MatCardModule,
    MatFormFieldModule,
    FormsModule,
    MatSelectModule,
    MatStepperModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatIconModule,
    ReactiveFormsModule,
    RouterModule.forRoot(routes),
    BrowserAnimationsModule,
    HttpClientModule,
    MatDialogModule,
    MatSnackBarModule,
    GoogleMapsModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
