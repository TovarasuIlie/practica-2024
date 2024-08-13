package com.PracticaVara.springJwt.controller;

import com.PracticaVara.springJwt.model.APIMessage;
import com.PracticaVara.springJwt.model.Account.User;
import com.PracticaVara.springJwt.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;


@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/Authentification")
@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService, HttpServletRequest servletRequest) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("register")
    public ResponseEntity<APIMessage> register(@RequestBody User request, HttpServletRequest servletRequest) {
            return authenticationService.register(request, servletRequest);
    }

    @PostMapping("login")
    public CompletableFuture<ResponseEntity<Object>> login(@RequestBody User request, HttpServletRequest servletRequest){
        return authenticationService.authenticate(request, servletRequest);
    }

    @GetMapping("refresh-page")
    public ResponseEntity<Object> refreshPage(HttpServletRequest servletRequest) {
        return authenticationService.refreshPage(servletRequest);
    }
}
