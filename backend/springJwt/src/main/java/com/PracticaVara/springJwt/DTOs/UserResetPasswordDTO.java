package com.PracticaVara.springJwt.DTOs;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResetPasswordDTO {

    @NotEmpty(message = "Codul este obligatoriu.")
    private String code;

    @NotEmpty(message = "Parola este obligatorie.")
    @Size(min = 8, max = 100, message = "Parola trebuie sa contina minim {min} caractere si maxim {max} caractere.")
    private String password;
}
