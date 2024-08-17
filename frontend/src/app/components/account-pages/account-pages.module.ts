import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AccountPagesRoutingModule } from './account-pages-routing.module';
import { IndexPageComponent } from './index-page/index-page.component';
import { SharedModule } from "../shared-components/shared.module";
import { MessagesPageComponent } from './messages-page/messages-page.component';
import { ProfilePageComponent } from './profile-page/profile-page.component';
import { ConfirmEmailPageComponent } from './confirm-email-page/confirm-email-page.component';
import { MatInputModule } from '@angular/material/input';
import { FavoriteAdsPageComponent } from './favorite-ad-page/favorite-ad-page.component';
import { SharedPipesModule } from '../../shared-pipes/shared-pipes.module';

@NgModule({
  declarations: [
    IndexPageComponent,
    MessagesPageComponent,
    ProfilePageComponent,
    ConfirmEmailPageComponent,
    FavoriteAdsPageComponent,
  ],

  imports: [
    CommonModule,
    AccountPagesRoutingModule,
    SharedModule,
    MatInputModule,
    SharedPipesModule
]
})
export class AccountPagesModule { }
