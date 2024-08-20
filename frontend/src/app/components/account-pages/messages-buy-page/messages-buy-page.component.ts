import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AnnouncementService } from '../../../services/announcement.service';
import { ChatroomService } from '../../../services/chatroom.service';
import { AuthService } from '../../../services/auth.service';
import { Announcement } from '../../../models/announcement';
import { Chatroom, ChatroomMessage } from '../../../models/chatroom';
import Stomp from 'stompjs';
import SockJS from 'sockjs-client';
import { environment } from '../../../../environments/environment.development';

@Component({
  selector: 'app-messages-buy-page',
  templateUrl: './messages-buy-page.component.html',
  styleUrl: './messages-buy-page.component.css'
})
export class MessagesBuyPageComponent {
  textLimit: number = 33;
  ad!: Announcement;
  chatSelected!: number;
  chatrooms: Chatroom[] = [];
  chatroomMessages: ChatroomMessage[] = []
  chatroom: Chatroom = <Chatroom>{}
  sendMessageForm: FormGroup = new FormGroup({});
  stompClient: any;

  constructor(private adService: AnnouncementService, private chatroomService: ChatroomService, public authService: AuthService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.chatroomService.getMyBuyChatrooms().subscribe(chatrooms => {
      this.chatrooms = chatrooms;
    });
    this.initilizeForm();
    this.initializeWebSocketConnection();
  }

  initializeWebSocketConnection() {
    const ws = new SockJS(environment.API_URL + "/ws");
    this.stompClient = Stomp.over(ws);
    this.stompClient.debug = null;
    const that = this;
    this.stompClient.connect({}, function(frame: any) {
      that.stompClient.subscribe('/message', (message: any) => {
        if (message.body) {
          that.chatroomMessages.push(JSON.parse(message.body));
        }
        console.log(that.chatroomMessages);
      });
    });
  }

  selectChat(id: string) {
    this.chatroomMessages = [];
    this.chatroom = this.chatrooms.filter((x) => {return x.id === id})[0];
    this.chatroomService.getMessageFromChatroom(id).subscribe(messages => {
      this.chatroomMessages = messages;
      console.log(messages);
    });
    console.log(this.chatroom);
    console.log(this.chatrooms);
  }

  getImage(fileName: string, index: string) {
    return "http://localhost:8080/ads-imgs/" + fileName + "/" + fileName + "-" + index + ".jpeg";
  }

  initilizeForm() {
    this.sendMessageForm = this.fb.group({
      message: [null]
    })
  }

  sendMessage() {
    if(this.sendMessageForm.value.message != null && this.sendMessageForm.value.message != "") {
      this.stompClient.send('/app/send/send-message-to-chatroom' , {}, JSON.stringify({message: this.sendMessageForm.value.message, token: this.authService.getJWT(), chatroomID: this.chatroom.id}));
      this.sendMessageForm.reset();
    }
  }
}
