export interface Category {
    id: number;
    name: string;
    searchLink: string;
    iconUrl: string;
    image: File;
}

export interface CategoryEdit {
    id: number;
    nameEdit: string;
    image: File;
}
