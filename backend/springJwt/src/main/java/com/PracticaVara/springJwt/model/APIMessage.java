package com.PracticaVara.springJwt.model;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class APIMessage {
    private HttpStatus httpStatus;
    private String message;

    public APIMessage(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
