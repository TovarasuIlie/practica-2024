import { UserAdmin } from "./user";

export interface SuspendDays {
    text: string;
    numberOfDays: number;
}

export interface SuspendDetail {
    id: number;
    adminUsername: string;
    startingDate: string;
    endingDate: string;
    permanentSuspend: boolean;
    suspendReason: string;
    ipAddress: string;
}

export interface SuspendAccount {
    id: number;
    adminUsername: string;
    startingDate: string;
    endingDate: string;
    permanentSuspend: boolean;
    suspendReason: string;
    ipAddress: string;
}