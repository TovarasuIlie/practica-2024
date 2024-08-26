import { Component } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Title } from '@angular/platform-browser';
import { ConfirmEmail } from '../../../models/user';
import { ToastService } from '../../../services/toast.service';

@Component({
  selector: 'app-confirm-email-page',
  templateUrl: './confirm-email-page.component.html',
  styleUrl: './confirm-email-page.component.css'
})
export class ConfirmEmailPageComponent {
  emailConfirmed: boolean = false;

  progressBarValue: number = 0;

  constructor(private authService: AuthService, private router: Router, private activatedRoute: ActivatedRoute, private title: Title, private toastService: ToastService) {
    this.title.setTitle("Confirmare Email");
  }

  ngOnInit(): void {
    this.activatedRoute.queryParamMap.subscribe({
      next: (params: any) => {
        const confirmEmail: ConfirmEmail = {
          email: params.get("email"),
          token: params.get("token")
        }
        var setID = setInterval(() => {
          this.progressBarValue += Math.floor(Math.random() * 50);
          if(this.progressBarValue >= 100) {
            this.progressBarValue = 100;
            this.authService.confirmEmail(confirmEmail).subscribe({
              next: (response: any) => {
                clearInterval(setID);
                this.toastService.show({title: "Confirmare Email", message: response.message, classname: "text-success"});
                this.router.navigateByUrl('/')
              },
              error: (response) => {
                clearInterval(setID);
                this.toastService.show({title: "Confirmare Email", message: response.error.message, classname: "text-danger"});
                this.router.navigateByUrl('/')
              }
            })
          }
        }, 700)
      }
    });
  }
}
