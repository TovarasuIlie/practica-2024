import { Injectable} from '@angular/core';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot } from '@angular/router';
import { Observable, map } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { ToastService } from '../services/toast.service';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root'
})

export class UnauthGuard {
  constructor(private authService: AuthService, private toasterService: ToastService, private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    return this.authService.user$.pipe(
      map((user: User | null) => {
        if(user) {
          this.toasterService.show({title: "Acces interzis!", message: "Pentru a intra pe acea pagina trebuie sa nu fi autentificat!", classname: "text-danger"});
          this.router.navigateByUrl('/');
          return false;
        } else {
          return true;
        }
      })
    );
  }
}
