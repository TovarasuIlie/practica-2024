import { Component, OnInit } from '@angular/core';
import { AnnouncementService } from '../../../services/announcement.service';
import { Announcement } from '../../../models/announcement';
import { ChatroomService } from '../../../services/chatroom.service';
import { Chatroom, ChatroomMessage } from '../../../models/chatroom';
import { AuthService } from '../../../services/auth.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import Stomp from 'stompjs';
import SockJS from 'sockjs-client';
import { environment } from '../../../../environments/environment.development';
import { User } from '../../../models/user';

@Component({
  selector: 'app-messages-sell-page',
  templateUrl: './messages-sell-page.component.html',
  styleUrl: './messages-sell-page.component.css'
})
export class MessagesSellPageComponent implements OnInit {
  textLimit: number = 20;
  ad!: Announcement;
  chatrooms: Chatroom[] = [];
  chatroomMessages: ChatroomMessage[] = []
  chatroom: Chatroom = <Chatroom>{}
  sendMessageForm: FormGroup = new FormGroup({});
  stompClient: any;
  chatSelectedID!: string;
  loadingMessages: boolean = true;
  loadingChatrooms: boolean = true;
  chatroomUsers: User[] = [];

  constructor(private adService: AnnouncementService, private chatroomService: ChatroomService, public authService: AuthService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.chatroomService.getMySellChatrooms().subscribe(chatrooms => {
      this.chatrooms = chatrooms;
      for(let i = 0; i < chatrooms.length; i++) {
        if(typeof(chatrooms[i].buyer) == "object") {
          this.chatroomUsers.push(chatrooms[i].buyer);
        } else {
          this.chatrooms[i].buyer = this.chatroomUsers[(this.chatrooms[i].buyer as any) - 1]
        }
      }
      this.loadingChatrooms = false;
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
      });
    });
  }

  selectChat(id: string) {
    this.chatSelectedID = id;
    this.chatroomMessages = [];
    this.chatroom = this.chatrooms.filter((x) => {return x.id === id})[0];
    this.chatroomService.getMessageFromChatroom(id).subscribe(messages => {
      this.chatroomMessages = messages;
      this.loadingMessages = false;
    });
  }

  getImage(fileName: string, index: string) {
    return environment.API_URL + "/ads-imgs/" + fileName + "/" + fileName + "-" + index + ".jpeg";
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
