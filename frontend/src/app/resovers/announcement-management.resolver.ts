import { ResolveFn } from '@angular/router';
import { AnnouncementManageService } from '../services/announcement-manage.service';
import { inject } from '@angular/core';
import { catchError, of } from 'rxjs';

export const announcementManagementResolver: ResolveFn<boolean> = (route, state) => {
  const adService = inject(AnnouncementManageService);
  
  return adService.getAdById(route.params['id']).pipe(
    catchError((response) => {
      return of(response);
    })
  )
};
