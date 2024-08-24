package com.PracticaVara.springJwt.service.AccountServices;

import java.time.LocalDateTime;
import java.util.Optional;

import com.PracticaVara.springJwt.model.APIMessage;
import com.PracticaVara.springJwt.model.Account.Role;
import com.PracticaVara.springJwt.model.Account.User;
import com.PracticaVara.springJwt.model.LogHistory;
import com.PracticaVara.springJwt.repository.LogHistoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.PracticaVara.springJwt.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final LogHistoryRepository logHistoryRepository;

    public UserService(UserRepository userRepository, LogHistoryRepository logHistoryRepository){
        this.userRepository = userRepository;
        this.logHistoryRepository = logHistoryRepository;
    }

    public ResponseEntity<?> findAllUsersOrdered(){
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAllByOrderByRoleAscIdAsc());
    }

    public ResponseEntity<?> findUserById(Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findById(id));
    }

    public ResponseEntity<?> updateUser(Integer id, User updatedUserDetails) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> currentAdmin = userRepository.findByUsername(username);
        Optional<User> existingUser = userRepository.findById(id);

        if(currentAdmin.isPresent()){
            User admin = currentAdmin.get();
            if(existingUser.isPresent()){
                User existingUserDetails = existingUser.get();
                existingUserDetails.setFirstName(updatedUserDetails.getFirstName());
                existingUserDetails.setLastName(updatedUserDetails.getLastName());
                existingUserDetails.setUsername(updatedUserDetails.getUsername());
                existingUserDetails.setEmail(updatedUserDetails.getEmail());
                existingUserDetails.setAddress(updatedUserDetails.getAddress());
                userRepository.save(existingUserDetails);

                LogHistory newLogHistoryAdmin = new LogHistory();
                newLogHistoryAdmin.setUser(admin);
                newLogHistoryAdmin.setAction("A editat detaliile utilizatorului " + updatedUserDetails.getUsername());
                newLogHistoryAdmin.setIpAddress(admin.getIpAddress());
                newLogHistoryAdmin.setActionDate(LocalDateTime.now());
                logHistoryRepository.save(newLogHistoryAdmin);

                LogHistory newLogHistoryUser = new LogHistory();
                newLogHistoryUser.setUser(updatedUserDetails);
                newLogHistoryUser.setAction("A avut detaliile editate de catre administratorul " + admin.getUsername());
                newLogHistoryUser.setIpAddress(admin.getIpAddress());
                newLogHistoryUser.setActionDate(LocalDateTime.now());
                logHistoryRepository.save(newLogHistoryUser);

                return ResponseEntity.status(HttpStatus.OK).body(new APIMessage(HttpStatus.OK, "Utilizator editat cu succes."));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Utilizatorul nu exista."));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Utilizatorul cu rol de ADMIN nu exista."));
        }


    }

    public ResponseEntity<?> updateRole(Integer id, Role role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> currentUser = userRepository.findByUsername(username);

        if(currentUser.isPresent()){
            User user = currentUser.get();
            if(!user.getId().equals(id)) {
                Optional<User> existingUser = userRepository.findById(id);
                if (existingUser.isPresent()) {
                    String previousRole = existingUser.get().getRole().toString();
                    existingUser.get().setRole(role);
                    userRepository.save(existingUser.get());

                    LogHistory newLogHistoryAdmin = new LogHistory();
                    newLogHistoryAdmin.setUser(user);
                    newLogHistoryAdmin.setAction("A schimbat rolul utilizatorului " + existingUser.get().getUsername() + " in rolul de "+ existingUser.get().getRole());
                    newLogHistoryAdmin.setIpAddress(user.getIpAddress());
                    newLogHistoryAdmin.setActionDate(LocalDateTime.now());
                    logHistoryRepository.save(newLogHistoryAdmin);

                    LogHistory newLogHistoryUser = new LogHistory();
                    newLogHistoryUser.setUser(existingUser.get());
                    newLogHistoryUser.setAction("I-a fost schimbat rolul din " + previousRole + " in rolul de "+ existingUser.get().getRole());
                    newLogHistoryUser.setIpAddress(existingUser.get().getIpAddress());
                    newLogHistoryUser.setActionDate(LocalDateTime.now());
                    logHistoryRepository.save(newLogHistoryUser);

                    return ResponseEntity.status(HttpStatus.OK).body(new APIMessage(HttpStatus.OK, "Utilizator editat cu succes."));
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Utilizatorul nu exista."));
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new APIMessage(HttpStatus.UNAUTHORIZED, "Nu iti poti schimba rolul singur!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Utilizatorul cu rol de ADMIN nu exista."));
        }


    }

    public ResponseEntity<?> deleteUser(Integer id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> currentAdmin = userRepository.findByUsername(username);
        Optional<User> existingUser = userRepository.findById(id);

        if(currentAdmin.isPresent()){
            User admin = currentAdmin.get();
            if(existingUser.isPresent()){
                User user = existingUser.get();
                userRepository.deleteById(id);

                LogHistory newLogHistoryAdmin = new LogHistory();
                newLogHistoryAdmin.setUser(admin);
                newLogHistoryAdmin.setAction("A sters utilizatorul " +user.getUsername());
                newLogHistoryAdmin.setIpAddress(admin.getIpAddress());
                newLogHistoryAdmin.setActionDate(LocalDateTime.now());
                logHistoryRepository.save(newLogHistoryAdmin);
                return ResponseEntity.status(HttpStatus.OK).body(new APIMessage(HttpStatus.OK, "Utilizatorul o fost sters cu succes."));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Utilizatorul nu exista."));
            }
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Utilizatorul cu rol de ADMIN nu exista."));
        }




    }


    public ResponseEntity<?> confirmEmail(Integer id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> currentAdmin = userRepository.findByUsername(username);

        if(currentAdmin.isPresent()){
            User admin = currentAdmin.get();
            Optional<User> currentUser = userRepository.findById(id);
            if(currentUser.isPresent()){
                User user = currentUser.get();
                user.setEmailVerifed(true);
                userRepository.save(user);

                LogHistory newLogHistoryAdmin = new LogHistory();
                newLogHistoryAdmin.setUser(admin);
                newLogHistoryAdmin.setAction("A confirmat adresa de email a utilizatorului " + user.getUsername());
                newLogHistoryAdmin.setIpAddress(admin.getIpAddress());
                newLogHistoryAdmin.setActionDate(LocalDateTime.now());
                logHistoryRepository.save(newLogHistoryAdmin);

                LogHistory newLogHistoryUser = new LogHistory();
                newLogHistoryUser.setUser(user);
                newLogHistoryUser.setAction("A avut adresa de email confirmata de catre administratorul " + admin.getUsername());
                newLogHistoryUser.setIpAddress(user.getIpAddress());
                newLogHistoryUser.setActionDate(LocalDateTime.now());
                logHistoryRepository.save(newLogHistoryUser);

                return ResponseEntity.status(HttpStatus.OK).body(new APIMessage(HttpStatus.OK, "Emailul a fost confirmat."));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Emailul nu exista."));
            }

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Utilizatorul cu rol de ADMIN nu exista."));
        }





    }
}
