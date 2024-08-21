package com.PracticaVara.springJwt.DTOs;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserConfirmEmailDTO {
    @NotEmpty(message = "Adresa de email este obligatorie.")
    private String email;

    @NotEmpty(message = "Tokenul este obligatoriu.")
    private String token;

}
