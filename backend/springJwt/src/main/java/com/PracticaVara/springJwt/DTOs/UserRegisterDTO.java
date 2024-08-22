package com.PracticaVara.springJwt.DTOs;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTO {
    @NotEmpty(message = "Numele de familie este obligatoriu")
    private String firstName;

    @NotEmpty(message = "Prenumele este obligatoriu.")
    private String lastName;

    @NotEmpty(message = "Adresa de email este obligatorie.")
    private String email;

    @NotEmpty(message = "Numele de utilizator este obligatoriu.")
    @Size(min = 8, message = "Numele de utilizator trebuie sa aiba minim {min} caractere")
    private String username;

    @Size(min = 8, max = 100, message = "Parola trebuie sa contina minim {min} caractere si maxim {max} caractere.")
    @NotEmpty(message = "Parola este obligatorie.")
    private String password;

    @NotEmpty(message = "Judetul este obligatoriu.")
    private String address;
}
