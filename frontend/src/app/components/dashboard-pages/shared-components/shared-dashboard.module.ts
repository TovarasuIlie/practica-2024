import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SideMenuComponent } from './side-menu/side-menu.component';
import { RouterModule } from '@angular/router';
import { FooterComponent } from './footer/footer.component';
import { SharedModule } from "../../shared-components/shared.module";



@NgModule({
  declarations: [
    SideMenuComponent,
    FooterComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    SharedModule
  ],
  exports: [
    SideMenuComponent,
    FooterComponent
  ]
})
export class SharedDashboardModule { }
