package com.PracticaVara.springJwt.controller;

import com.PracticaVara.springJwt.model.APIMessage;
import com.PracticaVara.springJwt.model.AuthenticationResponse;
import com.PracticaVara.springJwt.model.User;
import com.PracticaVara.springJwt.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/Authentification")
public class AuthenticationController {

    @Autowired
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("register")
    public ResponseEntity<APIMessage> register(@RequestBody User request) {
            return authenticationService.register(request);
    }

    @PostMapping("login")
    public ResponseEntity<Object> login(
            @RequestBody User request
    ){
        return authenticationService.authenticate(request);
    }

    @PostMapping("/admin_only")
    public ResponseEntity<String> adminOnly(){
        return ResponseEntity.ok("Hello from admin only URL");
    }
}
