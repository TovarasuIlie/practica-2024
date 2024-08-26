import { Category } from "./category";
import { User } from "./user";

export interface Announcement {
    id: number;
    title: string;
    content: string;
    category: Category;
    createdDate: string;
    expirationDate: string;
    imageUrl: string;
    user: any;
    deactivated: boolean,
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
    category: Category;
    image: File[];
    county: string;
    address: string;
    contactPersonName: string;
    phoneNumber: string;
    price: number;
    currency: string;
}

export interface EditAnnouncement {
    title: string;
    county: string;
    content: string;
    price: number;
    currency: string;
    address: string;
    contactPersonName: string;
    phoneNumber: string;
}