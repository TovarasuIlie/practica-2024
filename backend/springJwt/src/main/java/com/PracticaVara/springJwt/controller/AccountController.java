package com.PracticaVara.springJwt.controller;

import com.PracticaVara.springJwt.model.APIMessage;
import com.PracticaVara.springJwt.service.AccountService;
import com.PracticaVara.springJwt.service.EmailService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/Account")
@AllArgsConstructor
@RestController
public class AccountController {
    private final AccountService accountService;
    private final EmailService emailService;

    @GetMapping("forgot-password/{email}")
    public ResponseEntity<APIMessage> forgotPassword(@PathVariable("email") String email) {
        return accountService.forgotPassword(email);
    }

    @PostMapping("reset-password")
    public ResponseEntity<APIMessage> resetPassword(@RequestBody JsonNode requestBody) {
        return accountService.resetPassword(requestBody);
    }

    @GetMapping("send-email")
    public void sendEmail() {
        emailService.sendEmail();
    }
}
