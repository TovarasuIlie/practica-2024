import { ResolveFn } from '@angular/router';
import { Announcement } from '../models/announcement';
import { AnnouncementService } from '../services/announcement.service';
import { inject } from '@angular/core';
import { catchError, Observable, of } from 'rxjs';

export const annoucementResolver: ResolveFn<Announcement> = (route, state): Observable<Announcement> => {
  const adService = inject(AnnouncementService);
  
  return adService.getAnnouncementByUrl(route.params['adTitle']).pipe(
    catchError((response) => {
      return of(response);
    })
  )
};
