import { Component, OnInit } from '@angular/core';
import { AnnouncementService } from '../../../services/announcement.service';
import { Announcement } from '../../../models/announcement';

@Component({
  selector: 'app-messages-page',
  templateUrl: './messages-page.component.html',
  styleUrl: './messages-page.component.css'
})
export class MessagesPageComponent implements OnInit {
  textLimit: number = 33;
  ad!: Announcement;
  chatSelected!: number;
  text: string = 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut rutrum, est sed congue placerat, sapien tortor porttitor quam';

  constructor(private adService: AnnouncementService) {}

  ngOnInit(): void {
    this.adService.getAnnouncementById("25").subscribe(x => this.ad = x);
  }

  selectChat(index: number) {
    this.chatSelected = index;
  }

  getImage(fileName: string, index: string) {
    return "http://localhost:8080/ads-imgs/" + fileName + "/" + fileName + "-" + index + ".jpeg";
  }
}
