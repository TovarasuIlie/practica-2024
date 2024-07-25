import { HttpEvent } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpHandler, HttpRequest } from '@angular/common/http';

import { Observable, take } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    constructor(private authService: AuthService) {}
    
    intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
        this.authService.user$.pipe(take(1)).subscribe({
          next: user => {
            if (user) {
              // Clone from the coming request and add Authorization header to that
              request = request.clone({
                setHeaders: {
                  Authorization: `Bearer ${user.jwt}`
                }
              });
            }
          }
        })
        return next.handle(request);
      }
}