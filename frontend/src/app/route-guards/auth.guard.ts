import { Injectable} from '@angular/core';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { ToastService } from '../services/toast.service';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})

export class AuthGuard {
  constructor(private authService: AuthService, private toasterService: ToastService, private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<boolean> {
    return new Promise((resolve) => {
      if(localStorage.getItem(environment.USER_KEY)) {
        resolve(true)
      } else {
        this.toasterService.show({title: "Acces interzis!", message: "Pentru a intra pe acea pagina trebuie sa fi autentificat!", classname: "text-danger"});
        resolve(false)
      }
    })
  }
}
