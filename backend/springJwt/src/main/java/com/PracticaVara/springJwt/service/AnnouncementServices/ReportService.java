package com.PracticaVara.springJwt.service.AnnouncementServices;

import com.PracticaVara.springJwt.DTOs.ReportDTO;
import com.PracticaVara.springJwt.model.APIMessage;
import com.PracticaVara.springJwt.model.Account.User;
import com.PracticaVara.springJwt.model.Announcement;
import com.PracticaVara.springJwt.model.LogHistory;
import com.PracticaVara.springJwt.model.Report;
import com.PracticaVara.springJwt.repository.AnnouncementRepository;
import com.PracticaVara.springJwt.repository.LogHistoryRepository;
import com.PracticaVara.springJwt.repository.ReportRepository;
import com.PracticaVara.springJwt.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final AnnouncementRepository announcementRepository;
    private final LogHistoryRepository logHistoryRepository;


    public ReportService(ReportRepository reportRepository, UserRepository userRepository, AnnouncementRepository announcementRepository, LogHistoryRepository logHistoryRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.announcementRepository = announcementRepository;
        this.logHistoryRepository = logHistoryRepository;
    }

    public ResponseEntity<APIMessage> createReport(@Valid ReportDTO request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> currentUser = userRepository.findByUsername(username);
        if(currentUser.isPresent()){
            Optional<Announcement> currentAnnouncement = announcementRepository.findById(request.getAnnouncementId());
            if(currentAnnouncement.isPresent()){
                Announcement announcement = currentAnnouncement.get();
                User user = currentUser.get();
                Report report = new Report();
                report.setAnnouncement(announcement);
                report.setUser(user);
                report.setMessage(request.getMessage());
                report.setSolved(false);
                reportRepository.save(report);

                LogHistory newLogHistory = new LogHistory();
                newLogHistory.setUser(user);
                newLogHistory.setAction("A creat reportul cu id-ul " +report.getId());
                newLogHistory.setIpAddress(user.getIpAddress());
                newLogHistory.setActionDate(LocalDateTime.now());
                logHistoryRepository.save(newLogHistory);

                return  ResponseEntity.status(HttpStatus.CREATED).body(new APIMessage(HttpStatus.CREATED, "Reportul a fost realizat cu succes!"));

            } else {
                 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Anuntul nu exista."));
            }
        } else {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Utilizatorul nu exista."));
        }
    }
    public ResponseEntity<APIMessage> deleteReport(Integer reportId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> userOptional = userRepository.findByUsername(username);
        Optional<Report> reportOptional = reportRepository.findById(reportId);
        if (reportOptional.isPresent() && userOptional.isPresent()) {
            User user = userOptional.get();

            LogHistory newLogHistory = new LogHistory();
            newLogHistory.setUser(user);
            newLogHistory.setAction("A sters reportul cu id-ul " +reportId);
            newLogHistory.setIpAddress(user.getIpAddress());
            newLogHistory.setActionDate(LocalDateTime.now());
            logHistoryRepository.save(newLogHistory);

            reportRepository.deleteById(reportId);

            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new APIMessage(HttpStatus.NO_CONTENT, "Reportul a fost sters cu succes."));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIMessage(HttpStatus.NOT_FOUND, "Reportul nu exista."));
        }
    }

    public ResponseEntity<?> findAllReportsByUserOrdered(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userRepository.findByUsername(username);

        if(user.isPresent()){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(reportRepository.findAllByUserOrderBySolvedAscIdDesc(user.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND,"Utilizatorul nu exista"));
        }


    }
}
