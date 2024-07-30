import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthPagesRoutingModule } from './auth-pages-routing.module';
import { ForgotPasswordPageComponent } from './forgot-password-page/index-page/forgot-password-page.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../../shared-components/shared.module';
import { ResetPasswordPageComponent } from './forgot-password-page/reset-password-page/reset-password-page.component';


@NgModule({
  declarations: [
    ForgotPasswordPageComponent,
    ResetPasswordPageComponent,
  ],
  imports: [
    CommonModule,
    AuthPagesRoutingModule,
    SharedModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
  ]
})
export class AuthPagesModule { }
