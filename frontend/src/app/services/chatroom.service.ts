import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Chatroom, ChatroomMessage } from '../models/chatroom';
import { environment } from '../../environments/environment.development';
import { delay } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ChatroomService {

  constructor(private http: HttpClient) { }

  getMessageFromChatroom(chatroomID: string) {
    return this.http.get<ChatroomMessage[]>(environment.API_URL + "/api/Chatrooms/get-all-messages-from-chatroom/" + chatroomID);
  }

  getChatroomID(announcemetID: number, sellerID: number, buyerID: number) {
    return this.http.get<Chatroom>(environment.API_URL + "/api/Chatrooms/get-or-create-chatroom?announcementID="+ announcemetID +"&sellerID=" + sellerID + "&buyerID=" + buyerID);
  }

  getMySellChatrooms() {
    return this.http.get<Chatroom[]>(environment.API_URL + "/api/Chatrooms/get-my-sell-chatrooms");
  }

  getMyBuyChatrooms() {
    return this.http.get<Chatroom[]>(environment.API_URL + "/api/Chatrooms/get-my-buy-chatrooms");
  }
}
