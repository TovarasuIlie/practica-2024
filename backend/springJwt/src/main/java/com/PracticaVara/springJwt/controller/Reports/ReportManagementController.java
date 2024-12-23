package com.PracticaVara.springJwt.controller.Reports;

import com.PracticaVara.springJwt.service.AnnouncementServices.ReportManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "${spring.originUrl}")
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

    @GetMapping("update-report-status/{id}")
    public ResponseEntity<?> updateReportStatus(@PathVariable Integer id) {
        return reportManagementService.updateReportStatus(id);
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
