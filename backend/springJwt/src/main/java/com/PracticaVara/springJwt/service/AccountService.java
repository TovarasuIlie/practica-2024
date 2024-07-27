package com.PracticaVara.springJwt.service;

import com.PracticaVara.springJwt.model.APIMessage;
import com.PracticaVara.springJwt.model.Account.ResetPasswordCode;
import com.PracticaVara.springJwt.model.Account.User;
import com.PracticaVara.springJwt.repository.ResetPasswordCodeRepository;
import com.PracticaVara.springJwt.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@AllArgsConstructor
@Service
public class AccountService {
    private final UserRepository userRepository;
    private final ResetPasswordCodeRepository resetPasswordCodeRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<APIMessage> forgotPassword(String email) {
        if(!userRepository.findByEmail(email).isEmpty()) {
            User user = userRepository.findByEmail(email).get();
            if(resetPasswordCodeRepository.findByUser(user).isEmpty()) {
                ResetPasswordCode resetPasswordCode = new ResetPasswordCode();
                resetPasswordCode.setCode(getSaltString());
                resetPasswordCode.setUser(user);
                resetPasswordCodeRepository.save(resetPasswordCode);
                return ResponseEntity.ok(new APIMessage(HttpStatus.OK, "Un email a fost trimis pe adresa de email asociata contului!"));
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new APIMessage(HttpStatus.CONFLICT, "Un cod a fost deja trimis."));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Numele de utilizator introdus nu este asociat nici unui cont. Te rugam sa reintroduci un nume te utilizator valid."));
        }
    }

    public ResponseEntity<APIMessage> resetPassword(JsonNode requestBody) {
        String code = requestBody.get("code").asText();
        String password = requestBody.get("password").asText();
        if(!code.isEmpty() && !password.isEmpty()) {
            if(!resetPasswordCodeRepository.findByCode(code).isEmpty()) {
                password = passwordEncoder.encode(password);
                ResetPasswordCode resetPasswordCode = resetPasswordCodeRepository.findByCode(code).get();
                User user = userRepository.findById(resetPasswordCode.getUser().getId()).get();
                user.setPassword(password);
                userRepository.save(user);
                resetPasswordCodeRepository.delete(resetPasswordCode);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(new APIMessage(HttpStatus.ACCEPTED, "Parola a fost resetata cu succes! Acum te poti conecta cu noua parola!"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Ati introdus un cod gresit!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIMessage(HttpStatus.BAD_REQUEST, "A avut loc o eroare, va rugam sa reincercati!"));
        }
    }

    private String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 6) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
}
