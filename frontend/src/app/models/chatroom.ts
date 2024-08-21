import { Announcement } from "./announcement";
import { User } from "./user";

export interface Chatroom {
    id: string;
    announcement: Announcement,
    seller: User,
    buyer: User,
    createdAt: string
}

export interface ChatroomMessage {
    id: number;
    sender: any;
    message: string;
    sendAt: string;
}