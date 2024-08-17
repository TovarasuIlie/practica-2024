import { NgModule } from '@angular/core';
import { AsyncPipe, CommonModule } from '@angular/common';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { SearchBarComponent } from './search-bar/search-bar.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatSelectModule } from '@angular/material/select';
import { RouterModule } from '@angular/router';
import { LoginModalComponent } from './login-modal/login-modal.component';
import { ErrorStateMatcher, ShowOnDirtyErrorStateMatcher } from '@angular/material/core';
import { ToastComponent } from './toast/toast.component';
import { NgbToastModule } from '@ng-bootstrap/ng-bootstrap';
import { AdChatComponent } from './ad-chat/ad-chat.component';
import { SharedPipesModule } from '../../shared-pipes/shared-pipes.module';



@NgModule({
  declarations: [
    HeaderComponent,
    FooterComponent,
    SearchBarComponent,
    LoginModalComponent,
    ToastComponent,
    AdChatComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatAutocompleteModule,
    MatProgressSpinnerModule,
    MatSelectModule,
    MatCheckboxModule,
    AsyncPipe,
    RouterModule,
    NgbToastModule,
    SharedPipesModule
  ],
  exports: [
    HeaderComponent,
    FooterComponent,
    SearchBarComponent,
    ToastComponent,
    AdChatComponent
  ],
  providers: [
    {provide: ErrorStateMatcher, useClass: ShowOnDirtyErrorStateMatcher}
  ]
})
export class SharedModule { }
