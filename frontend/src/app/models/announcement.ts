import { User } from "./user";

export interface Announcement {
    id: number;
    title: string;
    content: string;
    createdDate: string;
    expirationDate: string;
    imageUrl: string;
    user: User;
    isDeactivated: boolean,
    price: number;
    currency: string;
    url: string;
    address: string;
    contactPersonName: string;
    phoneNumber: string;
    photoNumber: number;
    approved: boolean;
}

export interface AddAnnouncement {
    title: string;
    content: string;
    image: File[];
    country: string;
    address: string;
    contactPersonName: string;
    phoneNumber: string;
    price: number;
    currency: string;
}

export interface EditAnnouncement {
    title: string;
    content: string;
    address: string;
    contactPersonName: string;
    phoneNumber: string;
    price: number;
    currency: string;
}