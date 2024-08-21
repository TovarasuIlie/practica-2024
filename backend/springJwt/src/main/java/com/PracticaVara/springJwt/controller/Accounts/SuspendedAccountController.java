package com.PracticaVara.springJwt.controller.Accounts;

import com.PracticaVara.springJwt.service.AccountServices.SuspendedAccountService;
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

    @PutMapping("suspend-account/{id}")
    public ResponseEntity<?> suspendAccount(@PathVariable Integer id,  @RequestParam int numberOfDaysSuspended, @RequestParam String reason) {
        return suspendedAccountService.createSuspendedAccount(id, numberOfDaysSuspended, reason);
    }

    @DeleteMapping("unsuspend/{id}")
    public ResponseEntity<?> unsuspendAccount(@PathVariable Integer id) {
        return suspendedAccountService.removeSuspendedAccountById(id);
    }

}
