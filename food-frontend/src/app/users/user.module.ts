import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { GoogleMapsModule } from '@angular/google-maps';

import { UserRoutingModule } from './user-routing.module';

import { UserDashboardComponent } from './user-dashboard/user-dashboard.component';
import { FoodComponent } from './food/food.component';
import { WishlistComponent } from './wishlist/wishlist.component';
import { CartComponent } from './cart/cart.component';
import { OrdersComponent } from './orders/orders.component';
import { ProfileComponent } from '../profile/profile.component';
import { PaymentComponent } from './payment/payment.component';
import { TrackingComponent } from './tracking/tracking.component';
import { CustomersupportComponent } from '../customersupport/customersupport.component';
import { LivechatComponent } from '../livechat/livechat.component';
import { WalletComponent } from '../wallet/wallet.component';
import { SmartOrderAssistantComponent } from '../smart-order-assistant/smart-order-assistant.component';
import { SettingsComponent } from '../settings/settings.component';

@NgModule({
  declarations: [
    UserDashboardComponent,
    FoodComponent,
    WishlistComponent,
    CartComponent,
    OrdersComponent,
    ProfileComponent,
    PaymentComponent,
    TrackingComponent,
    CustomersupportComponent,
    LivechatComponent,
    WalletComponent,
    SmartOrderAssistantComponent,
    SettingsComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatSnackBarModule,
    GoogleMapsModule,
    UserRoutingModule,
  ],
})
export class UserModule {}
