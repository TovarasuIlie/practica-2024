package com.PracticaVara.springJwt.service;

import com.PracticaVara.springJwt.model.APIMessage;
import com.PracticaVara.springJwt.model.Account.User;
import com.PracticaVara.springJwt.model.Announcement;
import com.PracticaVara.springJwt.model.Report;
import com.PracticaVara.springJwt.repository.AnnouncementRepository;
import com.PracticaVara.springJwt.repository.ReportRepository;
import com.PracticaVara.springJwt.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final AnnouncementRepository announcementRepository;


    public ReportService(ReportRepository reportRepository, UserRepository userRepository, AnnouncementRepository announcementRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.announcementRepository = announcementRepository;
    }

    public ResponseEntity<APIMessage> createReport(JsonNode request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userRepository.findByUsername(username);

        if(user.isPresent()){
            Optional<Announcement> announcement = announcementRepository.findById(request.get("announcementId").asInt());
            if(announcement.isPresent()){
                Report report = new Report();
                report.setAnnouncement(announcement.get());
                report.setUser(user.get());
                report.setMessage(request.get("message").asText());
                report.setSolved(false);
                reportRepository.save(report);
                return  ResponseEntity.status(HttpStatus.CREATED).body(new APIMessage(HttpStatus.CREATED, "Reportul a fost realizat cu succes!"));

            } else {
                 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Anuntul nu exista."));
            }
        } else {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Utilizatorul nu exista."));
        }
    }
    public ResponseEntity<APIMessage> deleteReport(Integer reportId) {
        Optional<Report> reportOptional = reportRepository.findById(reportId);
        if (reportOptional.isPresent()) {
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
