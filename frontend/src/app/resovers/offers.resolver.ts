import { ResolveFn } from '@angular/router';
import { Announcement } from '../models/announcement';
import { inject } from '@angular/core';
import { AnnouncementService } from '../services/announcement.service';
import { catchError, of } from 'rxjs';

export const offersResolver: ResolveFn<Announcement[]> = (route, state) => {
  const adService = inject(AnnouncementService);

  return adService.getAnnouncementsByUsername(route.params["username"]).pipe(
    catchError((response) => {
      return of(response);
    })
  )
};
