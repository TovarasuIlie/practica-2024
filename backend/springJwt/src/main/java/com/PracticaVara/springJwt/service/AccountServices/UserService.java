package com.PracticaVara.springJwt.service.AccountServices;

import java.util.Optional;

import com.PracticaVara.springJwt.model.APIMessage;
import com.PracticaVara.springJwt.model.Account.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.PracticaVara.springJwt.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> findAllUsersOrdered(){
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAllByOrderByRoleAscIdAsc());
    }

    public ResponseEntity<?> findUserById(Integer id){

        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findById(id));
    }
    public ResponseEntity<?> updateUser(Integer id, User updatedUserDetails) {
        Optional<User> existingUser = userRepository.findById(id);
        if(existingUser.isPresent()){
            User existingUserDetails = existingUser.get();
            //Aici eu am pus toate la rand, te rog scoate daca ceva nu are sens sa fie pus aici
            //s-a modificat putin 14.08.2024
            existingUserDetails.setFirstName(updatedUserDetails.getFirstName());
            existingUserDetails.setLastName(updatedUserDetails.getLastName());
            existingUserDetails.setUsername(updatedUserDetails.getUsername());
            existingUserDetails.setEmail(updatedUserDetails.getEmail());
            existingUserDetails.setAddress(updatedUserDetails.getAddress());
            userRepository.save(existingUserDetails);
            return ResponseEntity.status(HttpStatus.OK).body(new APIMessage(HttpStatus.OK, "Utilizator editat cu succes."));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Utilizatorul nu exista."));
        }

    }

    public ResponseEntity<?> deleteUser(Integer id){
        userRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new APIMessage(HttpStatus.OK, "Utilizatorul o fost sters cu succes."));
    }


    public ResponseEntity<?> confirmEmail(Integer id){
        Optional<User> currentUser = userRepository.findById(id);
        if(currentUser.isPresent()){
            User user = currentUser.get();
            user.setEmailVerifed(true);
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(new APIMessage(HttpStatus.OK, "Emailul a fost confirmat."));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Emailul nu exista."));
        }



    }
}
