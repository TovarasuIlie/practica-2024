export interface User {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
    jwt: string;
    registeredDate: string;
    address: string;
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

export interface ConfirmEmail {
    email: string;
    token: string;
}
