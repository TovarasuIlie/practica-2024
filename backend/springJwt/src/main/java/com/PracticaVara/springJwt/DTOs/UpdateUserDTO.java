package com.PracticaVara.springJwt.DTOs;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDTO {

    @NotEmpty
    private Integer id;
    @NotEmpty(message = "Numele de familie este obligatoriu")
    private String firstName;
    @NotEmpty(message = "Prenumele este obligatoriu!")
    private String lastName;
    @NotEmpty(message = "Adresa este obligatore!")
    private String address;
    @Pattern(regexp = "\\d{10}", message = "Numarul de telefon trebuie sa contina exact 10 cifre.")
    @NotEmpty(message = "Numarul de telefon este obligatoriu")
    private String phoneNumber;
}
