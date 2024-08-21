package com.PracticaVara.springJwt.DTOs;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {
    @NotNull(message = "Id-ul anuntului este obligatoriu.")
    private Integer announcementId;

    @NotEmpty(message = "Mesajul este obligatoriu.")
    private String message;

}
