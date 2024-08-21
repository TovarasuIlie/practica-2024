import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { Announcement } from '../../../models/announcement';
import { AuthService } from '../../../services/auth.service';
import { Chatroom, ChatroomMessage } from '../../../models/chatroom';
import { ChatroomService } from '../../../services/chatroom.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import Stomp from 'stompjs';
import SockJS from 'sockjs-client';
import { environment } from '../../../../environments/environment.development';

@Component({
  selector: 'app-ad-chat',
  templateUrl: './ad-chat.component.html',
  styleUrl: './ad-chat.component.css'
})
export class AdChatComponent implements OnChanges {
  @Input("chatroom") chatroom!: Chatroom;
  @Input("current-ad") currentAd!: Announcement;
  text: string = 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut rutrum, est sed congue placerat, sapien tortor porttitor quam';
  yourID!: number;
  chatroomMessages: ChatroomMessage[] = [];
  sendMessageForm: FormGroup = new FormGroup({});
  stompClient: any;
  loadingMessages: boolean = true;

  constructor(public authService: AuthService, private chatroomService: ChatroomService, private fb: FormBuilder) {

  }

  ngOnInit(): void {
    this.chatroom = <Chatroom>{};
    this.chatroom.announcement = this.currentAd;
    this.initilizeForm();
    this.initializeWebSocketConnection();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if(changes['chatroom'].currentValue && this.chatroomMessages.length == 0) {
      this.chatroom = changes['chatroom'].currentValue;
      this.initializeMessages();
    }
  }

  initializeMessages() {
    this.chatroomMessages = [];
    this.chatroomService.getMessageFromChatroom(this.chatroom.id).subscribe(messages => {
      this.chatroomMessages = messages;
      this.loadingMessages = false;
    });
  }

  initilizeForm() {
    this.sendMessageForm = this.fb.group({
      message: [null]
    })
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

  getImage(fileName: string, index: string) {
    return "http://localhost:8080/ads-imgs/" + fileName + "/" + fileName + "-" + index + ".jpeg";
  }

  sendMessage() {
    if(this.sendMessageForm.value.message != null && this.sendMessageForm.value.message != "") {
      this.stompClient.send('/app/send/send-message-to-chatroom' , {}, JSON.stringify({message: this.sendMessageForm.value.message, token: this.authService.getJWT(), chatroomID: this.chatroom.id}));
      this.sendMessageForm.reset();
    }
  }
}
