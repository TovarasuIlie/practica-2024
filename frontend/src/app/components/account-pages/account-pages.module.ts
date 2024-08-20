import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AccountPagesRoutingModule } from './account-pages-routing.module';
import { IndexPageComponent } from './index-page/index-page.component';
import { SharedModule } from "../shared-components/shared.module";
import { ProfilePageComponent } from './profile-page/profile-page.component';
import { ConfirmEmailPageComponent } from './confirm-email-page/confirm-email-page.component';
import { MatInputModule } from '@angular/material/input';
import { FavoriteAdsPageComponent } from './favorite-ad-page/favorite-ad-page.component';
import { SharedPipesModule } from '../../shared-pipes/shared-pipes.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MessagesBuyPageComponent } from './messages-buy-page/messages-buy-page.component';
import { MessagesSellPageComponent } from './messages-sell-page/messages-sell-page.component';

@NgModule({
  declarations: [
    IndexPageComponent,
    ProfilePageComponent,
    ConfirmEmailPageComponent,
    FavoriteAdsPageComponent,
    MessagesBuyPageComponent,
    MessagesSellPageComponent
  ],

  imports: [
    CommonModule,
    AccountPagesRoutingModule,
    SharedModule,
    MatInputModule,
    SharedPipesModule,
    FormsModule,
    ReactiveFormsModule
]
})
export class AccountPagesModule { }
