import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AccountPagesRoutingModule } from './account-pages-routing.module';
import { IndexPageComponent } from './index-page/index-page.component';
import { SharedModule } from "../shared-components/shared.module";
import { BrowserModule } from '@angular/platform-browser';
import { MessagesPageComponent } from './messages-page/messages-page.component';
import { ProfilePageComponent } from './profile-page/profile-page.component';
import { SettingsPageComponent } from './settings-page/settings-page.component';
import { ConfirmEmailPageComponent } from './confirm-email-page/confirm-email-page.component';

@NgModule({
  declarations: [
    IndexPageComponent,
    MessagesPageComponent,
    ProfilePageComponent,
    SettingsPageComponent,
    ConfirmEmailPageComponent
  ],

  imports: [
    CommonModule,
    AccountPagesRoutingModule,
    SharedModule
]
})
export class AccountPagesModule { }
