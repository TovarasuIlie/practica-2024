import { Announcement } from "./announcement";
import { User } from "./user";

export interface Offer {
    user: User;
    announcementList: Announcement[];
}