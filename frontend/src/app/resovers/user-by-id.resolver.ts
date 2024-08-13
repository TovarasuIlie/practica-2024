import { ResolveFn } from '@angular/router';
import { User } from '../models/user';
import { inject } from '@angular/core';
import { UserManagementService } from '../services/user-management.service';
import { catchError, of } from 'rxjs';

export const userByIdResolver: ResolveFn<User> = (route, state) => {
  const userManagementService = inject(UserManagementService);
  return userManagementService.getSpecificUser(route.params['id']).pipe(
    catchError((response) => {
      return of(response);
    })
  );
};
