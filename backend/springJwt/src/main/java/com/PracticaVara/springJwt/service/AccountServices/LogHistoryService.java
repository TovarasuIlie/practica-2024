package com.PracticaVara.springJwt.service.AccountServices;

import com.PracticaVara.springJwt.model.APIMessage;
import com.PracticaVara.springJwt.model.Account.User;
import com.PracticaVara.springJwt.model.LogHistory;
import com.PracticaVara.springJwt.repository.LogHistoryRepository;
import com.PracticaVara.springJwt.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogHistoryService {
    private final LogHistoryRepository logHistoryRepository;
    private final UserRepository userRepository;

    public LogHistoryService(LogHistoryRepository logHistoryRepository, UserRepository userRepository) {
        this.logHistoryRepository = logHistoryRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> getAllLogHistoryByUserID(Integer userId){

        Optional<User> currentUser = userRepository.findById(userId);
        if(currentUser.isPresent()){
            User user = currentUser.get();
            List<LogHistory> logHistories = logHistoryRepository.findAllByUser(user);
            return ResponseEntity.status(HttpStatus.OK).body(logHistories);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Utilizatorul nu exista."));
        }

    }
}
