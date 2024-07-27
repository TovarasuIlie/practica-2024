package com.PracticaVara.springJwt.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String jwt;

}
