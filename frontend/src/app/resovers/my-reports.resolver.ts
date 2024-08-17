import { ResolveFn } from '@angular/router';
import { Report } from '../models/report';
import { inject } from '@angular/core';
import { ReportService } from '../services/report.service';
import { catchError, of } from 'rxjs';

export const myReportsResolver: ResolveFn<Report[]> = (route, state) => {
  const reportService = inject(ReportService);
  return reportService.getMyReports().pipe(
    catchError((response) => {
      return of(response);
    })
  )
};
