package com.PracticaVara.springJwt.controller;

import com.PracticaVara.springJwt.model.APIMessage;
import com.PracticaVara.springJwt.model.Account.User;
import com.PracticaVara.springJwt.service.AuthenticationService;
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

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("register")
    public ResponseEntity<APIMessage> register(@RequestBody User request) {
            return authenticationService.register(request);
    }

    @PostMapping("login")
    public CompletableFuture<ResponseEntity<Object>> login(@RequestBody User request){
        return authenticationService.authenticate(request);
    }

    @GetMapping("refresh-page")
    public ResponseEntity<Object> refreshPage() {
        return authenticationService.refreshPage();
    }
}
