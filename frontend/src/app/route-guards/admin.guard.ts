import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, GuardResult, MaybeAsync, Router, RouterStateSnapshot } from '@angular/router';
import { map, Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { ToastService } from '../services/toast.service';
import { User } from '../models/user';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {
  constructor(private authService: AuthService, private toastService: ToastService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    return this.authService.user$.pipe(
      map((user: User | null) => {
        if(user) {
          const decodedToken: User = jwtDecode(user.jwt);
          if(decodedToken.role == "ROLE_ADMIN") {
            return true;
          }
        }
        this.toastService.show({title: "Acces interzis!", message: "Nu sunteti autorizat ca sa intrati pe aceasta pagina!", classname: "text-danger"});
        this.router.navigateByUrl('/');
        return false;
      }) 
    )
  }
  
}
