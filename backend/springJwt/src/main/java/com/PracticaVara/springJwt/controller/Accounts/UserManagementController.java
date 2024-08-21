package com.PracticaVara.springJwt.controller.Accounts;

import com.PracticaVara.springJwt.model.Account.User;
import com.PracticaVara.springJwt.service.AccountServices.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/UserManagement")
public class UserManagementController {
    private final UserService userService;

    public UserManagementController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("get-all-users")
    public ResponseEntity<?> getAllUsers(){
        return userService.findAllUsersOrdered();
    }

    @GetMapping("/get-user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id){
        return userService.findUserById(id);
    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody User updatedUserDetails) {
        return userService.updateUser(id, updatedUserDetails);

    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id){
        return userService.deleteUser(id);
    }

    @GetMapping("/confirm-email/{id}")
    public ResponseEntity<?> confirmEmailC(@PathVariable Integer id) {
        return userService.confirmEmail(id);
    }
}