import { Announcement } from "./announcement";
import { User } from "./user";

export interface Report {
    id: number;
    announcement: Announcement;
    user: User;
    message: string;
    solved: boolean;
}

export interface ReportInsert {
    announcementId: number;
    message: string;
}