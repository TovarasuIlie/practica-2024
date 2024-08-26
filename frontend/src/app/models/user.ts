import { SuspendDetail } from "./suspend";

export interface User {
    id: number;
    firstName: string;
    lastName: string;
    username: string;
    email: string;
    jwt: string;
    registeredDate: string;
    address: string;
    role: string;
}

export interface UserLogin {
    username: string;
    password: string;
}

export interface UserRegister {
    username: string;
    firstName: string;
    lastName: string;
    email: string;
    password: string;
    address: string;
    country: string;
}

export interface ConfirmEmail {
    email: string;
    token: string;
}

export interface UserAdmin {
    id: string;
    firstName: string;
    lastName: string;
    username: string;
    email: string;
    emailVerifed: boolean;
    role: string;
    registeredDate: string;
    address: string;
    ipAddress: string;
    ipLogs: IPLogs[];
    logHistoryList: LogHistory[]
    suspendDetails: SuspendDetail;
}

export interface UserEdit {
    firstName: string;
    lastName: string;
    email: string;
    address: string;
}

export interface EditProfile {
    firstName: string;
    lastName: string;
    address: string;
}

export interface IPLogs {
    ipAddress: string;
    usedFrom: string;
}

export interface LogHistory {
    ipAddress: string;
    action: string;
    actionDate: string;
}