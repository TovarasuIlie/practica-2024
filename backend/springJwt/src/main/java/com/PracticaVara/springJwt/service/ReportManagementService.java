package com.PracticaVara.springJwt.service;

import com.PracticaVara.springJwt.model.APIMessage;
import com.PracticaVara.springJwt.model.Account.User;
import com.PracticaVara.springJwt.model.Announcement;
import com.PracticaVara.springJwt.model.Report;
import com.PracticaVara.springJwt.repository.AnnouncementRepository;
import com.PracticaVara.springJwt.repository.ReportRepository;
import com.PracticaVara.springJwt.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportManagementService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final AnnouncementRepository announcementRepository;

    public ReportManagementService(ReportRepository reportRepository, UserRepository userRepository, AnnouncementRepository announcementRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.announcementRepository = announcementRepository;
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
    public ResponseEntity<?> updateReportStatus(Integer reportId, boolean status){
        Optional<Report> report = reportRepository.findById(reportId);
        if (report.isPresent()) {
            Report updatedReport = report.get();
            updatedReport.setSolved(status);
            reportRepository.save(updatedReport);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new APIMessage(HttpStatus.OK, "Statusul reportului a fost schimbat cu succes."));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIMessage(HttpStatus.NOT_FOUND, "Reportul nu exista."));
        }
    }
}
