import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserDashboardComponent } from './user-dashboard/user-dashboard.component';
import { FoodComponent } from './food/food.component';
import { WishlistComponent } from './wishlist/wishlist.component';
import { CartComponent } from './cart/cart.component';
import { OrdersComponent } from './orders/orders.component';

import { PaymentComponent } from './payment/payment.component';
import { TrackingComponent } from './tracking/tracking.component';
import { ProfileComponent } from '../profile/profile.component';
import { CustomersupportComponent } from '../customersupport/customersupport.component';
import { LivechatComponent } from '../livechat/livechat.component';
import { WalletComponent } from '../wallet/wallet.component';
import { SmartOrderAssistantComponent } from '../smart-order-assistant/smart-order-assistant.component';
import { SettingsComponent } from '../settings/settings.component';


const routes: Routes = [
  {
    path: '',
    component: UserDashboardComponent,
    children: [
      { path: 'food', component: FoodComponent },
      { path: 'wishlist', component: WishlistComponent },
      { path: 'cart', component: CartComponent },
      { path: 'orders', component: OrdersComponent },
      { path: 'profile', component: ProfileComponent },
      { path: 'payment', component: PaymentComponent },
      { path: 'tracking', component: TrackingComponent },
      { path: 'customer-support', component: CustomersupportComponent },
      { path: 'live-chat', component: LivechatComponent },
      { path: 'wallet', component: WalletComponent },
      { path: 'smart-order-assistant', component: SmartOrderAssistantComponent },
      { path: 'settings', component: SettingsComponent },
      { path: '', redirectTo: 'food', pathMatch: 'full' }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule {}
