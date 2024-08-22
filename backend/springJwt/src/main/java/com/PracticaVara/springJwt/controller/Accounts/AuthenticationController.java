package com.PracticaVara.springJwt.controller.Accounts;

import com.PracticaVara.springJwt.DTOs.UserLoginDTO;
import com.PracticaVara.springJwt.DTOs.UserRegisterDTO;
import com.PracticaVara.springJwt.model.APIMessage;
import com.PracticaVara.springJwt.model.Account.User;
import com.PracticaVara.springJwt.service.AccountServices.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;


@CrossOrigin(origins = "${spring.originUrl}")
@RequestMapping("api/Authentification")
@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService, HttpServletRequest servletRequest) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("register")
    public ResponseEntity<APIMessage> register(@RequestBody UserRegisterDTO request, HttpServletRequest servletRequest) {
            return authenticationService.register(request, servletRequest);
    }

    @PostMapping("login")
    public CompletableFuture<ResponseEntity<Object>> login(@Valid @RequestBody UserLoginDTO request, HttpServletRequest servletRequest){
        return authenticationService.authenticate(request, servletRequest);
    }

    @GetMapping("refresh-page")
    public ResponseEntity<Object> refreshPage(HttpServletRequest servletRequest) {
        return authenticationService.refreshPage(servletRequest);
    }
}
