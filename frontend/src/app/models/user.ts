export interface User {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
    jwt: string;
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
}