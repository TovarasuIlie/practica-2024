import { User } from "./user";

export interface Announcement {
    id: number;
    title: string;
    content: string;
    createdDate: string;
    expirationDate: string;
    imageUrl: string;
    user: User;
    idDeactivated: boolean,
    price: number;
    currency: string;
    url: string;
    address: string;
    contactPersonName: string;
    phoneNumber: string;
    photoNumber: number;
}

export interface AddAnnouncement {
    title: string;
    content: string;
    image: File[]
    address: string;
    contactPersonName: string;
    phoneNumber: string;
    price: number;
    currency: string;
}