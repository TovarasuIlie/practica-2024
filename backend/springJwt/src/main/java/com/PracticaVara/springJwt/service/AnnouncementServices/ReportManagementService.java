package com.PracticaVara.springJwt.service.AnnouncementServices;

import com.PracticaVara.springJwt.model.APIMessage;
import com.PracticaVara.springJwt.model.Account.User;
import com.PracticaVara.springJwt.model.Announcement;
import com.PracticaVara.springJwt.model.LogHistory;
import com.PracticaVara.springJwt.model.Report;
import com.PracticaVara.springJwt.repository.AnnouncementRepository;
import com.PracticaVara.springJwt.repository.LogHistoryRepository;
import com.PracticaVara.springJwt.repository.ReportRepository;
import com.PracticaVara.springJwt.repository.UserRepository;
import com.PracticaVara.springJwt.service.AccountServices.LogHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReportManagementService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final AnnouncementRepository announcementRepository;
    private final LogHistoryRepository logHistoryRepository;

    public ReportManagementService(ReportRepository reportRepository, UserRepository userRepository, AnnouncementRepository announcementRepository, LogHistoryRepository logHistoryRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.announcementRepository = announcementRepository;

        this.logHistoryRepository = logHistoryRepository;
    }

    public ResponseEntity<?> getReportById(Integer reportId){
        Optional<Report> report = reportRepository.findById(reportId);
        if (report.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(report.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIMessage(HttpStatus.NOT_FOUND, "Reportul nu a fost gasit."));
        }
    }

    public ResponseEntity<?> getReportsByAnnouncement(Integer announcementId){
        Optional<Announcement> announcement = announcementRepository.findById(announcementId);
        if (announcement.isPresent()) {
            List<Report> reports = reportRepository.findAllByAnnouncementOrderBySolvedAscIdDesc(announcement.get());
            return ResponseEntity.status(HttpStatus.OK).body(reports);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIMessage(HttpStatus.NOT_FOUND, "Anuntul nu a fost gasit."));
        }
    }
    public ResponseEntity<?> getReportsByUser(Integer userId){
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<Report> reports = reportRepository.findAllByUserOrderBySolvedAscIdDesc(user.get());
            return ResponseEntity.status(HttpStatus.OK).body(reports);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIMessage(HttpStatus.NOT_FOUND, "Utilizatorul nu a fost gasit."));
        }
    }

    public ResponseEntity<?> getAllReports(){
        List<Report> reports = reportRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(reports);
    }
    public ResponseEntity<?> updateReportStatus(Integer reportId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> currentAdmin = userRepository.findByUsername(username);
        Optional<Report> report = reportRepository.findById(reportId);
        if (report.isPresent() && currentAdmin.isPresent()) {
            User admin = currentAdmin.get();

            Report updatedReport = report.get();
            updatedReport.setSolved(!updatedReport.isSolved());
            reportRepository.save(updatedReport);

            LogHistory newLogHistoryAdmin = new LogHistory();
            newLogHistoryAdmin.setUser(admin);
            newLogHistoryAdmin.setAction("A editat reportul creat de catre utilizatorul " + updatedReport.getUser().getUsername());
            newLogHistoryAdmin.setIpAddress(admin.getIpAddress());
            newLogHistoryAdmin.setActionDate(LocalDateTime.now());
            logHistoryRepository.save(newLogHistoryAdmin);

            LogHistory newLogHistoryUser = new LogHistory();
            newLogHistoryUser.setUser(updatedReport.getUser());
            newLogHistoryUser.setAction("Reportul cu id-ul " + updatedReport.getId() + " a fost editat de catre administratorul " +admin.getUsername());
            newLogHistoryUser.setIpAddress(updatedReport.getUser().getIpAddress());
            newLogHistoryUser.setActionDate(LocalDateTime.now());
            logHistoryRepository.save(newLogHistoryUser);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new APIMessage(HttpStatus.OK, "Statusul reportului a fost schimbat cu succes."));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIMessage(HttpStatus.NOT_FOUND, "Reportul nu exista."));
        }
    }
}
