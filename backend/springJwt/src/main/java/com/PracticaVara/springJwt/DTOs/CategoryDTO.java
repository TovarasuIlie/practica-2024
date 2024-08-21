package com.PracticaVara.springJwt.DTOs;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    @NotEmpty(message = "Numele este obligatoriu.")
    @Size(min = 3, max = 20, message = "Numele trebuie sa contina intre {min} si {max} caractere.")
    private String name;
}
