package com.PracticaVara.springJwt.DTOs;

import com.PracticaVara.springJwt.model.Account.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private int id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String jwt;
    private LocalDateTime registeredDate;
    private Role role;
    private String address;
}
