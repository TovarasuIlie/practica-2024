package com.PracticaVara.springJwt.service;

import com.PracticaVara.springJwt.model.APIMessage;
import com.PracticaVara.springJwt.model.AuthenticationResponse;
import com.PracticaVara.springJwt.model.Role;
import com.PracticaVara.springJwt.model.User;
import com.PracticaVara.springJwt.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public ResponseEntity<APIMessage> register(User request){
        if(repository.findByUsername(request.getUsername()).isEmpty() && repository.findByEmail(request.getEmail()).isEmpty()) {
            User user = new User();
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(Role.USER);
            user.setRegisteredDate(LocalDateTime.now());

            repository.save(user);

            return ResponseEntity.ok(new APIMessage(HttpStatus.CREATED, "Contul a fost creat cu succes! Pentru a putea activa contul, trebuie sa accesati link-ul de pe email."));
        } else {
            if(!repository.findByUsername(request.getUsername()).isEmpty()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new APIMessage(HttpStatus.CONFLICT, "Acest nume de utilizator este deja folost!"));
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new APIMessage(HttpStatus.CONFLICT, "Acest email a fost deja folost pentru alt cont!"));
            }
        }
    }

    public ResponseEntity<Object> authenticate(User request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        User user = repository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user);

        return ResponseEntity.ok(user);
    }
}
