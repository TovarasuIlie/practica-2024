package com.PracticaVara.springJwt.service;

import com.PracticaVara.springJwt.DTOs.UserDTO;
import com.PracticaVara.springJwt.interceptors.BearerTokenWrapper;
import com.PracticaVara.springJwt.model.Account.User;
import com.PracticaVara.springJwt.repository.UserRepository;
import com.PracticaVara.springJwt.model.APIMessage;
import com.PracticaVara.springJwt.model.Account.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class AuthenticationService {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final BearerTokenWrapper tokenWrapper;
    private final EmailService emailService;

    public AuthenticationService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, BearerTokenWrapper tokenWrapper, EmailService emailService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.tokenWrapper = tokenWrapper;
        this.emailService = emailService;
    }

    @Async("asyncTaskExecutor")
    public ResponseEntity<APIMessage> register(User request) {
        if(repository.findByUsername(request.getUsername()).isEmpty()) {
            if(repository.findByEmail(request.getEmail()).isEmpty()) {
                User user = new User();
                user.setFirstName(request.getFirstName());
                user.setLastName(request.getLastName());
                user.setUsername(request.getUsername());
                user.setEmail(request.getEmail());
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                user.setRole(Role.USER);
                user.setRegisteredDate(LocalDateTime.now());

                repository.save(user);
                try {
                    emailService.sendHtmlVerificationEmail(user);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new APIMessage(HttpStatus.CONFLICT, "Acesta adresa de email este deja folosita!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new APIMessage(HttpStatus.CONFLICT, "Acest nume de utilizator este deja folosit!"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new APIMessage(HttpStatus.OK, "Contul a fost creat. Vei primi un email pentru ati confirma confirma contul!"));
    }

    @Async("asyncTaskExecutor")
    public CompletableFuture<ResponseEntity<Object>> authenticate(User request) {
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            if(auth.isAuthenticated()) {
                User user = repository.findByUsername(request.getUsername()).orElseThrow();
                user.setJwt(jwtService.generateToken(user));
                return CompletableFuture.completedFuture(ResponseEntity.ok(new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getJwt(), user.getRegisteredDate(), user.getRole())));
            }
        } catch (AuthenticationException e) {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIMessage(HttpStatus.BAD_REQUEST, "Numele de utilizator / Email-ul sau Parola sunt gresite!")));
        }
        return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIMessage(HttpStatus.INTERNAL_SERVER_ERROR, "A avut loc o eroare!")));
    }

//    @Async("asyncTaskExecutor")
    public ResponseEntity<Object> refreshPage() {
        String jwt = tokenWrapper.getToken();
        if(!jwtService.isTokenExpired(jwt)) {
            Optional<User> user = repository.findByUsername(jwtService.extractUsername(jwt));
            user.get().setJwt(jwt);
            if(jwtService.isValid(jwt, user.get())) {
                return ResponseEntity.ok(new UserDTO(user.get().getId(), user.get().getUsername(), user.get().getEmail(), user.get().getFirstName(), user.get().getLastName(), user.get().getJwt(), user.get().getRegisteredDate(), user.get().getRole()));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIMessage(HttpStatus.BAD_REQUEST, "A aparut o eroare la generarea token-ului!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIMessage(HttpStatus.BAD_REQUEST, "Token-ul a exipirat!"));
        }
    }
}
