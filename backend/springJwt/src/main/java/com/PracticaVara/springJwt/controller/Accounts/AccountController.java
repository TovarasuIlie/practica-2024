package com.PracticaVara.springJwt.controller.Accounts;

import com.PracticaVara.springJwt.DTOs.UserConfirmEmailDTO;
import com.PracticaVara.springJwt.DTOs.UserResetPasswordDTO;
import com.PracticaVara.springJwt.model.APIMessage;
import com.PracticaVara.springJwt.service.AccountServices.AccountService;
import com.PracticaVara.springJwt.service.AccountServices.EmailService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
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
    public ResponseEntity<APIMessage> resetPassword(@RequestBody UserResetPasswordDTO requestBody) {
        return accountService.resetPassword(requestBody);
    }

    @PutMapping("confirm-email")
    public ResponseEntity<APIMessage> confirmEmail(@RequestBody UserConfirmEmailDTO requestBody) {
        return accountService.confirmEmail(requestBody);
    }
}
