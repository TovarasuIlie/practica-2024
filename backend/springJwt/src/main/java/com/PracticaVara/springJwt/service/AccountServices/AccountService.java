package com.PracticaVara.springJwt.service.AccountServices;

import com.PracticaVara.springJwt.DTOs.UpdateUserDTO;
import com.PracticaVara.springJwt.DTOs.UserConfirmEmailDTO;
import com.PracticaVara.springJwt.DTOs.UserResetPasswordDTO;
import com.PracticaVara.springJwt.model.APIMessage;
import com.PracticaVara.springJwt.model.Account.ResetPasswordCode;
import com.PracticaVara.springJwt.model.Account.User;
import com.PracticaVara.springJwt.model.LogHistory;
import com.PracticaVara.springJwt.repository.LogHistoryRepository;
import com.PracticaVara.springJwt.repository.ResetPasswordCodeRepository;
import com.PracticaVara.springJwt.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@AllArgsConstructor
@Service
public class AccountService {
    private final UserRepository userRepository;
    private final ResetPasswordCodeRepository resetPasswordCodeRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final LogHistoryRepository logHistoryRepository;


    public ResponseEntity<APIMessage> forgotPassword(String email) {
        if(userRepository.findByEmail(email).isPresent()) {
            User user = userRepository.findByEmail(email).get();
            if(resetPasswordCodeRepository.findByUser(user).isEmpty()) {
                ResetPasswordCode resetPasswordCode = new ResetPasswordCode();
                resetPasswordCode.setCode(getSaltString());
                resetPasswordCode.setUser(user);
                resetPasswordCodeRepository.save(resetPasswordCode);

                LogHistory newLogHistory = new LogHistory();
                newLogHistory.setUser(user);
                newLogHistory.setAction("Solicitare parola.");
                newLogHistory.setIpAddress(user.getIpAddress());
                newLogHistory.setActionDate(LocalDateTime.now());
                logHistoryRepository.save(newLogHistory);

                try {
                    emailService.sendHtmlCodeEmail(user, resetPasswordCode.getCode());
                } catch (Exception e) {
                    //e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIMessage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
                }
                return ResponseEntity.ok(new APIMessage(HttpStatus.OK, "Un email a fost trimis pe adresa de email asociata contului!"));
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new APIMessage(HttpStatus.CONFLICT, "Un cod a fost deja trimis."));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Numele de utilizator introdus nu este asociat nici unui cont. Te rugam sa reintroduci un nume te utilizator valid."));
        }
    }

    public ResponseEntity<APIMessage> resetPassword(UserResetPasswordDTO requestBody) {
        String code = requestBody.getCode();
        String password = requestBody.getPassword();
        if(!code.isEmpty() && !password.isEmpty()) {
            if(resetPasswordCodeRepository.findByCode(code).isPresent()) {
                password = passwordEncoder.encode(password);
                ResetPasswordCode resetPasswordCode = resetPasswordCodeRepository.findByCode(code).get();
                User user = userRepository.findById(resetPasswordCode.getUser().getId()).get();
                user.setPassword(password);
                userRepository.save(user);

                LogHistory newLogHistory = new LogHistory();
                newLogHistory.setUser(user);
                newLogHistory.setAction("Resetare parola.");
                newLogHistory.setIpAddress(user.getIpAddress());
                newLogHistory.setActionDate(LocalDateTime.now());
                logHistoryRepository.save(newLogHistory);


                resetPasswordCodeRepository.delete(resetPasswordCode);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(new APIMessage(HttpStatus.ACCEPTED, "Parola a fost resetata cu succes! Acum te poti conecta cu noua parola!"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Ati introdus un cod gresit!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIMessage(HttpStatus.BAD_REQUEST, "A avut loc o eroare, va rugam sa reincercati!"));
        }
    }

    public ResponseEntity<APIMessage> confirmEmail(UserConfirmEmailDTO requestBody) {
        String email = requestBody.getEmail();
        String token = requestBody.getToken();
        if(!email.isEmpty() && !token.isEmpty()) {
            Optional<User> user = userRepository.findByEmail(email);
            if(user.isPresent()) {
                user.get().setEmailVerifed(true);
                userRepository.save(user.get());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIMessage(HttpStatus.BAD_REQUEST, "Nu a fost gasit nici un cont asociat email-ului! Te rugam reincearca!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIMessage(HttpStatus.BAD_REQUEST, "A aparut o eroare, te rugam sa reincerci!"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new APIMessage(HttpStatus.OK, "Email a fost confirmat cu succes! Acuma poti sa intri pe cont!"));
    }

    public static String getSaltString() {
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

    public ResponseEntity<?> updateProfile(UpdateUserDTO requestBody) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByUsername(authentication.getName()).get();

        currentUser.setFirstName(requestBody.getFirstName());
        currentUser.setLastName(requestBody.getLastName());
        currentUser.setAddress(requestBody.getAddress());

        LogHistory newLogHistory = new LogHistory();
        newLogHistory.setUser(currentUser);
        newLogHistory.setAction("Resetare parola.");
        newLogHistory.setIpAddress(currentUser.getIpAddress());
        newLogHistory.setActionDate(LocalDateTime.now());
        logHistoryRepository.save(newLogHistory);
        userRepository.save(currentUser);
        return ResponseEntity.ok(currentUser);
    }

    public ResponseEntity<?> getAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByUsername(authentication.getName()).get();
        return ResponseEntity.ok(currentUser);
    }
}
