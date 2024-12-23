package com.PracticaVara.springJwt.service.AccountServices;

import com.PracticaVara.springJwt.model.APIMessage;
import com.PracticaVara.springJwt.model.Account.User;
import com.PracticaVara.springJwt.model.LogHistory;
import com.PracticaVara.springJwt.model.SuspendedAccount;
import com.PracticaVara.springJwt.repository.LogHistoryRepository;
import com.PracticaVara.springJwt.repository.SuspendedAccountRepository;
import com.PracticaVara.springJwt.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class SuspendedAccountService {
    private final SuspendedAccountRepository suspendedAccountRepository;
    private final UserRepository userRepository;
    private final LogHistoryRepository logHistoryRepository;

    public SuspendedAccountService(SuspendedAccountRepository suspendedAccountRepository, UserRepository userRepository, LogHistoryRepository logHistoryRepository) {
        this.suspendedAccountRepository = suspendedAccountRepository;
        this.userRepository = userRepository;
        this.logHistoryRepository = logHistoryRepository;
    }

    public ResponseEntity<?> getAllSuspendedAccounts() {
        return ResponseEntity.ok(suspendedAccountRepository.findAll());
    }

    public ResponseEntity<?> getSuspendedAccountById(Integer id) {
        Optional<SuspendedAccount> suspendedAccount = suspendedAccountRepository.findById(id);
        if (suspendedAccount.isPresent()) {
            return ResponseEntity.ok(suspendedAccount.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Nu s-a gasit acest utilizator cu id " + id));
    }

    public ResponseEntity<?> createSuspendedAccount(Integer userId, int numberOfDaysSuspended, String reason ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> admin = userRepository.findByUsername(username);
        Optional<User> user = userRepository.findById(userId);
        if (admin.isPresent()) {
            if(user.isPresent()) {
                User suspendedUser = user.get();
                User adminUser = admin.get();
                LocalDateTime now = LocalDateTime.now();
                boolean isPermanent = false;
                if(numberOfDaysSuspended == 0){
                    isPermanent = true;
                }
                SuspendedAccount suspendedAccount = new SuspendedAccount();
                suspendedAccount.setUserSuspend(suspendedUser);
                suspendedAccount.setAdmin(adminUser);
                suspendedAccount.setPermanentSuspend(isPermanent);
                suspendedAccount.setSuspendReason(reason);
                suspendedAccount.setIpAddress(suspendedUser.getIpAddress());
                suspendedAccount.setStartingDate(now);
                suspendedAccount.setEndingDate(now.plusDays(numberOfDaysSuspended));
                suspendedAccountRepository.save(suspendedAccount);

                LogHistory newLogHistoryAdmin = new LogHistory();
                newLogHistoryAdmin.setUser(adminUser);
                newLogHistoryAdmin.setAction("A suspendat utilizatorul " + suspendedUser.getUsername() + "pana pe data " +suspendedAccount.getEndingDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))+". \n Motiv: " + suspendedAccount.getSuspendReason());
                newLogHistoryAdmin.setIpAddress(adminUser.getIpAddress());
                newLogHistoryAdmin.setActionDate(LocalDateTime.now());
                logHistoryRepository.save(newLogHistoryAdmin);

                LogHistory newLogHistoryUser = new LogHistory();
                newLogHistoryUser.setUser(suspendedUser);
                newLogHistoryUser.setAction("A fost suspendat de catre " + suspendedAccount.getAdminUsername() + "pana pe data " +suspendedAccount.getEndingDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))+ ". Motiv: " + suspendedAccount.getSuspendReason());
                newLogHistoryUser.setIpAddress(suspendedUser.getIpAddress());
                newLogHistoryUser.setActionDate(LocalDateTime.now());
                logHistoryRepository.save(newLogHistoryUser);


                return ResponseEntity.status(HttpStatus.CREATED).body(new APIMessage(HttpStatus.CREATED, "Utilizatorul " +suspendedUser.getUsername() + " a fost suspendat pana pe data de: " + suspendedAccount.getEndingDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Utilizatorul nu a fost gasit."));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Utilizatorul admin nu a fost gasit."));
        }

    }

    public ResponseEntity<?> removeSuspendedAccountById(Integer id) {
        Optional<SuspendedAccount> suspendedAccount = suspendedAccountRepository.findById(id);
        if (suspendedAccount.isPresent()) {
            suspendedAccountRepository.delete(suspendedAccount.get());

            LogHistory newLogHistoryAdmin = new LogHistory();
            newLogHistoryAdmin.setUser(suspendedAccount.get().getAdmin());
            newLogHistoryAdmin.setAction("A revocat suspendarea utilizatorului: " + suspendedAccount.get().getUserSuspend().getUsername());
            newLogHistoryAdmin.setIpAddress(suspendedAccount.get().getAdmin().getIpAddress());
            newLogHistoryAdmin.setActionDate(LocalDateTime.now());
            logHistoryRepository.save(newLogHistoryAdmin);

            LogHistory newLogHistoryUser = new LogHistory();
            newLogHistoryUser.setUser(suspendedAccount.get().getUserSuspend());
            newLogHistoryUser.setAction("A fost revocata suspendarea de catre " + suspendedAccount.get().getAdminUsername());
            newLogHistoryUser.setIpAddress(suspendedAccount.get().getUserSuspend().getIpAddress());
            newLogHistoryUser.setActionDate(LocalDateTime.now());
            logHistoryRepository.save(newLogHistoryUser);

            return ResponseEntity.status(HttpStatus.OK).body(new APIMessage(HttpStatus.OK, "Utilizatorul " + suspendedAccount.get().getUserSuspend().getUsername()+ " nu mai este suspendat." ));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Utilizatorul nu a fost gasit."));
        }
    }


}
