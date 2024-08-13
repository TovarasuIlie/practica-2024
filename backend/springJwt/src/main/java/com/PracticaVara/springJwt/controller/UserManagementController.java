package com.PracticaVara.springJwt.controller;

import com.PracticaVara.springJwt.model.Account.User;
import com.PracticaVara.springJwt.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/UserManagement")
public class UserManagementController {
    private final UserService userService;

    public UserManagementController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("get-all-users")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.findAllUsersOrdered();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/get-user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id){
        User user = userService.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User updatedUserDetails) {
        try {
            User updatedUser = userService.updateUser(id, updatedUserDetails);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Integer id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/confirm-email/{id}")
    public ResponseEntity<Void> confirmEmail(@PathVariable Integer id) {
        userService.confirmEmail(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
