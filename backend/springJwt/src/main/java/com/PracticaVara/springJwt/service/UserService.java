package com.PracticaVara.springJwt.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import com.PracticaVara.springJwt.model.Account.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.PracticaVara.springJwt.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void updateUserIpAdress(HttpServletRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            String ipAdress = request.getRemoteAddr();
            user.setIpAddress(ipAdress);
            userRepository.save(user);

        } else {
            throw new RuntimeException("User not found");
        }
    }

    public List<User> findAllUsersOrdered(){
        return userRepository.findAllByOrderByRoleAscIdAsc();
    }
    public User findUserById(Integer id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found."));

    }
    public User updateUser(Integer id, User updatedUserDetails) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        //Aici eu am pus toate la rand, te rog scoate daca ceva nu are sens sa fie pus aici
        existingUser.setFirstName(updatedUserDetails.getFirstName());
        existingUser.setLastName(updatedUserDetails.getLastName());
        existingUser.setUsername(updatedUserDetails.getUsername());
        existingUser.setEmail(updatedUserDetails.getEmail());
        existingUser.setAddress(updatedUserDetails.getAddress());
        return userRepository.save(existingUser);
    }

    public void deleteUser(Integer id){
        userRepository.deleteById(id);
        // sa nu uit sa bag si ceva mesaj idk
    }


    public void confirmEmail(Integer id){
        User user = findUserById(id);
        user.setEmailVerifed(true);
        userRepository.save(user);
    }
}
