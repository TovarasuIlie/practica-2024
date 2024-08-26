import { Announcement } from "./announcement";
import { LogHistory } from "./user";

export interface DashboardDetails {
    numberOfUsers: number;
    numberOfUsersToday: number;
    numberOfAnnouncements: number;
    numberOfAnnouncementsToday: number;
    numberOfSuspendedAccount: number;
    numberOfSuspendedAccountToday: number;
    numberOfUnsolvedReports: number;
    numberOfUnsolvedReportsToday: number;
    last24Ads: Announcement[];
    last24Logs: LogHistory[];
}