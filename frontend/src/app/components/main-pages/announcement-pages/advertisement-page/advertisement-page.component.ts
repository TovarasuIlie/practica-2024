import { Component, ElementRef, OnInit, QueryList, ViewChildren } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Announcement } from '../../../../models/announcement';
import { AuthService } from '../../../../services/auth.service';
import { AnnouncementService } from '../../../../services/announcement.service';
import { ToastService } from '../../../../services/toast.service';
import { ErrorStateMatcher } from '@angular/material/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReportService } from '../../../../services/report.service';
import { ChatroomService } from '../../../../services/chatroom.service';
import { Chatroom } from '../../../../models/chatroom';

@Component({
  selector: 'app-advertisement-page',
  templateUrl: './advertisement-page.component.html',
  styleUrl: './advertisement-page.component.css'
})
export class AdvertisementPageComponent implements OnInit {
  currentAd: Announcement = {} as Announcement;
  loadingAd: boolean = true;
  loadingEditButton: boolean = false;
  @ViewChildren("closeModal") closeModal!:  QueryList<ElementRef>;
  callSeller: string = "Suna Vanzatorul";
  errorMessages: string[] = [];
  matcher = new ErrorStateMatcher();
  reportForm: FormGroup = new FormGroup({});
  adChatroom!: Chatroom;
  yourID!: number;
  chatClicked: boolean = false;

  constructor(private activatedRoute: ActivatedRoute, public authService: AuthService, private adService: AnnouncementService, private toastService: ToastService, private router: Router,
              private fb: FormBuilder, private reportService: ReportService, private chatroomService: ChatroomService
  ) {
    this.authService.user$.subscribe({
      next: (value) => {
        if(value) {
          this.yourID = value?.id;
        }
      }
    });
  }

  ngOnInit(): void {
    this.initializeAd();
  }

  initializeAd() {
    this.adService.getAnnouncementByUrl(this.activatedRoute.snapshot.params['adTitle']).subscribe(ad => {
      this.currentAd = ad;
      this.loadingAd = false;
      this.initializeForm();
    })
  }

  initializeForm() {
    this.reportForm = this.fb.group({
      announcementId: [this.currentAd.id],
      message: [null, [Validators.required]],
    })
  }

  deleteAd() {
    this.adService.deleteAnnouncement(this.currentAd.id).subscribe({
      next: _ => {
        this.closeModal.forEach(element => {
          element.nativeElement.click();
        });
        this.toastService.show({title: "Anunt sters", message: "Anuntul a fost sters cu succes!", classname: "text-success"});
        this.router.navigateByUrl("/");
      }
    })
  }

  getImage(fileName: string, index: string) {
    return "http://localhost:8080/ads-imgs/" + fileName + "/" + fileName + "-" + index + ".jpeg";
  }

  changeText() {
    if(this.authService) {
      this.callSeller = this.currentAd.phoneNumber;
    } else {
      this.toastService.show({title: "Eroare", message: "Nu poti sa vezi numarul de telefon, daca nu esti logat.", classname: "text-danger"})
    }
  }

  reportAd() {
    if(this.reportForm.valid) {
      this.reportService.insertNewReport(this.reportForm.value).subscribe({
        next: (value: any) => {
          this.closeModal.forEach(element => {
            element.nativeElement.click();
          });
          this.toastService.show({title: "Raportat cu succes", message: value.message, classname: "text-success"})
        },
        error: (response) => {
          console.log(response);
        }
      })
    } else {
      this.errorMessages.push("Toate campurile trebuie completate!");
    }
  }

  openChat() {
    this.chatroomService.getChatroomID(this.currentAd.id, this.currentAd.user.id, this.yourID).subscribe(chatroom => {
      this.adChatroom = chatroom;
    })
  }
}
