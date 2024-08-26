package com.PracticaVara.springJwt.controller;

import com.PracticaVara.springJwt.DTOs.DashboardDetailsDTO;
import com.PracticaVara.springJwt.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "${spring.originUrl}")
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
    @Autowired
    private LogHistoryRepository logHistoryRepository;

    @GetMapping("get-all-details")
    public ResponseEntity<?> getAllDetails() {
        return ResponseEntity.ok(new DashboardDetailsDTO(
                userRepository.count(),
                userRepository.countTodayRegistredUsers(),
                announcementRepository.count(),
                announcementRepository.countTodayAnnouncements(),
                suspendedAccountRepository.count(),
                suspendedAccountRepository.countTodaySuspendedAccounts(),
                reportRepository.countBySolvedTrue(),
                0L,
                announcementRepository.last100Ads(),
                logHistoryRepository.last100Actions()
        ));
    }
}
