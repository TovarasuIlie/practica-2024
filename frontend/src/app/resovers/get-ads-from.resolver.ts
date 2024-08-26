import { ResolveFn } from '@angular/router';
import { Announcement } from '../models/announcement';
import { AnnouncementService } from '../services/announcement.service';
import { inject } from '@angular/core';
import { catchError, of } from 'rxjs';

export const getAdsFromResolver: ResolveFn<Announcement[]> = (route, state) => {
  const adService = inject(AnnouncementService);
  return adService.getAnnouncementsByUser(route.params["username"]).pipe(
    catchError((response) => {
      return of(response);
    })
  )
};
