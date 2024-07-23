package com.PracticaVara.springJwt.model;

import org.springframework.http.HttpStatus;

public class APIMessage {
    private HttpStatus status;
    private String message;

    public APIMessage(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public APIMessage() {
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
