package com.PracticaVara.springJwt.controller;

import com.PracticaVara.springJwt.service.SuspendedAccountService;
import io.micrometer.observation.annotation.Observed;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/Suspended-accounts")
public class SuspendedAccountController {
    private final SuspendedAccountService suspendedAccountService;

    public SuspendedAccountController(SuspendedAccountService suspendedAccountService) {
        this.suspendedAccountService = suspendedAccountService;
    }

    @GetMapping("all-suspended-accounts")
    public ResponseEntity<?> allSuspendedAccounts() {
        return suspendedAccountService.getAllSuspendedAccounts();
    }

    @GetMapping("suspended-account/{id}")
    public ResponseEntity<?> suspendedAccount(@PathVariable Integer id) {
        return suspendedAccountService.getSuspendedAccountById(id);
    }

    @GetMapping("suspend-account/{id}")
    public ResponseEntity<?> suspendAccount(@PathVariable("id") Integer id,  @RequestParam("numberOfDaysSuspended") Integer numberOfDaysSuspended, @RequestParam("reason") String reason) {
        return suspendedAccountService.createSuspendedAccount(id, numberOfDaysSuspended, reason);
    }

    @DeleteMapping("unsuspend-account/{id}")
    public ResponseEntity<?> unsuspendAccount(@PathVariable Integer id) {
        return suspendedAccountService.removeSuspendedAccountById(id);
    }

}
