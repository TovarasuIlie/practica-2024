import { Injectable} from '@angular/core';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { ToastService } from '../services/toast.service';
import { environment } from '../../environments/environment.development';
import { map, Observable } from 'rxjs';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root'
})

export class AuthGuard {
  constructor(private authService: AuthService, private toasterService: ToastService, private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    return this.authService.user$.pipe(
      map((user: User | null) => {
        if(user) {
          return true;
        } else {
          this.toasterService.show({title: "Acces interzis!", message: "Pentru a intra pe acea pagina trebuie sa fi autentificat!", classname: "text-danger"});
          this.router.navigateByUrl('/');
          return false;
        }
      })
    );
  }
}
