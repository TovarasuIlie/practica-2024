package com.PracticaVara.springJwt.controller;

import com.PracticaVara.springJwt.DTOs.DashboardDetailsDTO;
import com.PracticaVara.springJwt.repository.AnnouncementRepository;
import com.PracticaVara.springJwt.repository.ReportRepository;
import com.PracticaVara.springJwt.repository.SuspendedAccountRepository;
import com.PracticaVara.springJwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/DashboardDetails")
public class DashboardDetailsController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AnnouncementRepository announcementRepository;
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private SuspendedAccountRepository suspendedAccountRepository;

    @GetMapping("get-all-details")
    public ResponseEntity<?> getAllDetails() {
        return ResponseEntity.ok(new DashboardDetailsDTO(
                userRepository.count(),
                0L,
                announcementRepository.count(),
                0L,
                suspendedAccountRepository.count(),
                0L,
                reportRepository.countBySolvedTrue(),
                0L
        ));
    }
}
