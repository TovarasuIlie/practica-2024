import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { IndexPageComponent } from './components/main-pages/index-page/index-page.component';
import { SharedModule } from './components/shared-components/shared.module';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { AdvertisementPageComponent } from './components/main-pages/announcement-pages/advertisement-page/advertisement-page.component';
import { AddAdvertismentPageComponent } from './components/main-pages/announcement-pages/add-advertisment-page/add-advertisment-page.component';

import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { RegisterPageComponent } from './components/main-pages/auth-pages/register-page/register-page.component';
import { CommonModule } from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { AuthInterceptor } from './interceptors/auth.interceptor';

import { FilePondModule, registerPlugin } from 'ngx-filepond';
import FilePondPluginImagePreview from 'filepond-plugin-image-preview';
import FilePondPluginFileValidateType from 'filepond-plugin-file-validate-type';
import { EditAdvertisementPageComponent } from './components/main-pages/announcement-pages/edit-advertisement-page/edit-advertisement-page.component';
import { AllAdsFromComponent } from './components/main-pages/announcement-pages/all-ads-from/all-ads-from.component';
import { AdsListByCategoryComponent } from './components/main-pages/announcement-pages/ads-list-by-category/ads-list-by-category.component';
import { SharedPipesModule } from './shared-pipes/shared-pipes.module';
import { SearchForAdsPageComponent } from './components/main-pages/announcement-pages/search-for-ads-page/search-for-ads-page.component';

registerPlugin(FilePondPluginImagePreview);
registerPlugin(FilePondPluginFileValidateType);

@NgModule({
  declarations: [
    AppComponent,
    IndexPageComponent,
    AdvertisementPageComponent,
    AddAdvertismentPageComponent,
    RegisterPageComponent,
    EditAdvertisementPageComponent,
    AllAdsFromComponent,
    AdsListByCategoryComponent,
    SearchForAdsPageComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    BrowserAnimationsModule,
    FilePondModule,
    SharedPipesModule
  ],
  providers: [
    provideAnimationsAsync(),
    provideHttpClient(withInterceptorsFromDi()),
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
