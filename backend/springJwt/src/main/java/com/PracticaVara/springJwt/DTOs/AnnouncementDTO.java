package com.PracticaVara.springJwt.DTOs;

import com.PracticaVara.springJwt.model.Category;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.hibernate.annotations.processing.Pattern;
import jakarta.validation.constraints.Pattern;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementDTO {

    @Size(min = 16, max = 70, message = "Titlul trebuie sa aiba intre {min} si {max} caractere.")
    @NotEmpty(message = "Titlul este obligatoriu.")
    private String title;

    @NotEmpty(message = "Categoria este obligatorie.")
    private Category category;

    @Size(min = 40, max = 9000, message = "Descrierea trebuie sa contina intre {min} si {max} caractere.")
    @NotEmpty(message = "Descrierea este obligatorie.")
    private String content;

    @Size(min = 1, message = "Pretul trebuie sa fie de minim {minim} RON / EURO")
    @NotEmpty(message = "Pretul este obligatoriu.")
    private double price;

    @NotEmpty
    private String currency;

    @NotEmpty
    private String address;

//    @NotEmpty(message = "Este necesara cel putin o imagine.")
//    private MultipartFile[] imageFiles;

    @NotEmpty
    private String contactPersonName;

    @Pattern(regexp = "\\d{10}", message = "Numarul de telefon trebuie sa contina exact 10 cifre.")
    @NotEmpty(message = "Numarul de telefon este obligatoriu.")
    private String phoneNumber;

}
