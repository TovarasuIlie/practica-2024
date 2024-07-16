import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexPageComponent } from './components/main-pages/index-page/index-page.component';
import { AdvertisementPageComponent } from './components/main-pages/advertisement-page/advertisement-page.component';
import { AddAdvertismentPageComponent } from './components/main-pages/add-advertisment-page/add-advertisment-page.component';
import { RegisterPageComponent } from './components/main-pages/auth-pages/register-page/register-page.component';
import { ForgotPasswordPageComponent } from './components/main-pages/auth-pages/forgot-password-page/forgot-password-page.component';

const routes: Routes = [
  {
    path: '',
    component: IndexPageComponent
  },
  {
    path: "inregistreaza-te",
    component: RegisterPageComponent
  },
  {
    path: "reseteaza-parola",
    component: ForgotPasswordPageComponent
  },
  {
    path: 'anunt/:adTitle',
    component: AdvertisementPageComponent
  },
  {
    path: 'adauga-anunt',
    component: AddAdvertismentPageComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
