import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ForgotPasswordPageComponent } from './forgot-password-page/index-page/forgot-password-page.component';
import { ConfirmEmailPageComponent } from '../../account-pages/confirm-email-page/confirm-email-page.component';
import { ResetPasswordPageComponent } from './forgot-password-page/reset-password-page/reset-password-page.component';

const routes: Routes = [
  {
    path: '',
    component: ForgotPasswordPageComponent
  },
  {
    path: 'creaza-parola-noua',
    component: ResetPasswordPageComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthPagesRoutingModule { }
