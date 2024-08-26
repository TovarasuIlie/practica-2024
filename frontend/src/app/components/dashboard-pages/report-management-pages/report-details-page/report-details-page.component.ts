import { HttpErrorResponse } from '@angular/common/http';
import { Component, ElementRef, QueryList, ViewChildren } from '@angular/core';
import { ReportService } from '../../../../services/report.service';
import { Report } from '../../../../models/report';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../../../services/auth.service';
import { environment } from '../../../../../environments/environment.development';
import { ToastService } from '../../../../services/toast.service';
import { AnnouncementManageService } from '../../../../services/announcement-manage.service';

@Component({
  selector: 'app-repot-details-page',
  templateUrl: './report-details-page.component.html',
  styleUrl: './report-details-page.component.css'
})
export class ReportDetailsPageComponent {
  report!: Report;
  @ViewChildren("closeModal") closeModal!: QueryList<ElementRef>
  constructor(private reportService: ReportService, private activatedRoute: ActivatedRoute, public authService: AuthService, private toastService: ToastService, private adManageService: AnnouncementManageService,
              private router: Router
  ) {}

  ngOnInit(): void {
    this.initializeReport()
  }

  initializeReport() {
    this.activatedRoute.data.subscribe((response: any) => {
      if(!(response.report instanceof HttpErrorResponse)) {
        this.report = response.report;
      } else {
        this.report = {} as Report;
      }
    })
  }

  refreshReport() {
    this.reportService.getReportById(this.report.id).subscribe(
      report => this.report = report
    )
  }

  getImage(fileName: string, index: string) {
    return environment.API_URL + "/ads-imgs/" + fileName + "/" + fileName + "-" + index + ".jpeg";
  }

  updateStatus() {
    this.reportService.updateStatus(this.report.id).subscribe({
      next: _ => {
        this.refreshReport();
        this.closeModal.forEach(x => {
          x.nativeElement.click();
        })
        this.toastService.show({title: "Status raportare!", message: "Statusul a fost schmbat!", classname: "text-success"});
      }
    })
  }

  deleteAd() {
    this.adManageService.deleteAd(this.report.announcement.id).subscribe({
      next: (value: any) => {
        this.closeModal.forEach(x => x.nativeElement.click())
        this.toastService.show({title: "Anunt sters", message: "Anuntul a fost sters cu succes!", classname: "text-success"});
        this.router.navigateByUrl("/dashboard/raportari-anunturi");
      },
      error: (response) => {
        console.log(response)
      }
    })
  }

  rejectAd() {
    this.adManageService.markAsRejected(this.report.announcement.id).subscribe({
      next: (value: any) => {
        this.closeModal.forEach(x => x.nativeElement.click())
        this.refreshReport();
        this.toastService.show({title: "Anunt respins", message: value.message, classname: "text-success"})
      },
      error: (response) => {
        console.log(response)
      }
    })
  }

  logout() {
    this.authService.logOut();
    this.router.navigateByUrl("/");
    this.toastService.show({title: "Iesire din cont!", message: "Te-ai delogat cu succes!", classname: "text-success"});
  }
}
