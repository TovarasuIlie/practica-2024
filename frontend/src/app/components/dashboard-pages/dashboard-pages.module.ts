import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DashboardPagesRoutingModule } from './dashboard-pages-routing.module';
import { IndexPageComponent } from './index-page/index-page.component';
import { UsersListPageComponent } from './user-management/users-list-page/users-list-page.component';
import { CategoryPageComponent } from './category-page/category-page.component';

import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule} from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { UserDetailsPageComponent } from './user-management/user-details-page/user-details-page.component';
import { SharedDashboardModule } from './shared-components/shared-dashboard.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { AdsManagementComponent } from './ads-management-pages/ads-management/ads-management.component';
import { AdDetailsPageComponent } from './ads-management-pages/ad-details-page/ad-details-page.component';
import { FilePondModule } from 'ngx-filepond';
import { ReportsListPageComponent } from './report-management-pages/reports-list-page/reports-list-page.component';
import { ReportDetailsPageComponent } from './report-management-pages/report-details-page/report-details-page.component';


@NgModule({
  declarations: [
    IndexPageComponent,
    UsersListPageComponent,
    CategoryPageComponent,
    UserDetailsPageComponent,
    AdsManagementComponent,
    AdDetailsPageComponent,
    ReportsListPageComponent,
    ReportDetailsPageComponent
  ],
  imports: [
    CommonModule,
    DashboardPagesRoutingModule,
    SharedDashboardModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    FilePondModule
]
})
export class DashboardPagesModule { }
