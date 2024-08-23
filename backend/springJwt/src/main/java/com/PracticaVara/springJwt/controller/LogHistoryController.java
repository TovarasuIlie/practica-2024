package com.PracticaVara.springJwt.controller;

import com.PracticaVara.springJwt.service.AccountServices.LogHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "${spring.originUrl}")
@RestController
@RequestMapping("api/LogHistory")
public class LogHistoryController {
    private final LogHistoryService logHistoryService;

    public LogHistoryController(LogHistoryService logHistoryService) {
        this.logHistoryService = logHistoryService;
    }

    @GetMapping("get-user-log-history/{id}")
    public ResponseEntity<?> getUserLogHistory(@PathVariable Integer id) {
        return logHistoryService.getAllLogHistoryByUserID(id);
    }
}
