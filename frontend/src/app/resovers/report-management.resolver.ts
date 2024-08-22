import { ResolveFn } from '@angular/router';
import { Report } from '../models/report';
import { ReportService } from '../services/report.service';
import { inject } from '@angular/core';
import { catchError, of } from 'rxjs';

export const reportManagementResolver: ResolveFn<Report> = (route, state) => {
  const reportService = inject(ReportService);
  
  return reportService.getReportById(route.params['id']).pipe(
    catchError((response) => {
      return of(response);
    })
  )
};
