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
    @Size(min = 8, message = "Numele de utilizator trebuie sa aiba minim {min} caractere")
    @NotEmpty(message = "Numele de utilizator este obligatoriu.")
    private String username;

    @Size(min = 8, max = 100, message = "Parola trebuie sa contina minim {min} caractere si maxim {max} caractere.")
    @NotEmpty(message = "Parola este obligatorie")
    private String password;
}
