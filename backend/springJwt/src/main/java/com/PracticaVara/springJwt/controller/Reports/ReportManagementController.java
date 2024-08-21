package com.PracticaVara.springJwt.controller.Reports;

import com.PracticaVara.springJwt.service.AnnouncementServices.ReportManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/Reports-management")
public class ReportManagementController {
    private final ReportManagementService reportManagementService;

    public ReportManagementController(ReportManagementService reportManagementService) {
        this.reportManagementService = reportManagementService;
    }

    @GetMapping("get-reports")
    public ResponseEntity<?> getAllReports() {
        return reportManagementService.getAllReports();
    }

    @GetMapping("get-report/{id}")
    public ResponseEntity<?> getReportById(@PathVariable Integer id) {
        return reportManagementService.getReportById(id);
    }

    @PostMapping("update-report-status/{id}")
    public ResponseEntity<?> updateReportStatus(@PathVariable Integer id, @RequestParam boolean status) {
        return reportManagementService.updateReportStatus(id, status);
    }

    @GetMapping("get-reports-announcements/{id}")
    public ResponseEntity<?> getAllReportsByAnnouncementId(@PathVariable Integer announcementId){
        return reportManagementService.getReportsByAnnouncement(announcementId);
    }

    @GetMapping("get-reports-user/{id}")
    public ResponseEntity<?> getAllReportsByUserId(@PathVariable Integer userId){
        return reportManagementService.getReportsByUser(userId);
    }

}
