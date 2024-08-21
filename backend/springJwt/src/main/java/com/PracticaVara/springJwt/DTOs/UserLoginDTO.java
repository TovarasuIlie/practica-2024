package com.PracticaVara.springJwt.DTOs;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {
    @NotEmpty(message = "Numele de utilizator este obligatoriu.")
    @Size(min = 8, message = "Numele de utilizator trebuie sa aiba minim {min} caractere")
    private String username;

    @NotEmpty(message = "Parola este obligatorie")
    @Size(min = 8, max = 100, message = "Parola trebuie sa contina minim {min} caractere si maxim {max} caractere.")
    private String password;
}
