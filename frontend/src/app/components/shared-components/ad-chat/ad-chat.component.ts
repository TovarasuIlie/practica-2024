import { Component, Input } from '@angular/core';
import { Announcement } from '../../../models/announcement';

@Component({
  selector: 'app-ad-chat',
  templateUrl: './ad-chat.component.html',
  styleUrl: './ad-chat.component.css'
})
export class AdChatComponent {
  @Input("chat-for-ad") ad!: Announcement;
  text: string = 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut rutrum, est sed congue placerat, sapien tortor porttitor quam';

  getImage(fileName: string, index: string) {
    return "http://localhost:8080/ads-imgs/" + fileName + "/" + fileName + "-" + index + ".jpeg";
  }
}
