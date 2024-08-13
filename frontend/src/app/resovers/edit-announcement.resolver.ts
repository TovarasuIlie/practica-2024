import { ResolveFn } from '@angular/router';
import { Announcement } from '../models/announcement';
import { AnnouncementService } from '../services/announcement.service';
import { catchError, of } from 'rxjs';
import { inject } from '@angular/core';

export const editAnnouncementResolver: ResolveFn<Announcement> = (route, state) => {
  const adService = inject(AnnouncementService);
  
  return adService.getAnnouncementById(route.params['id']).pipe(
    catchError((response) => {
      return of(response);
    })
  )
};
