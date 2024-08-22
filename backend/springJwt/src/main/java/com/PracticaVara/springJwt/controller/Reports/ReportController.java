package com.PracticaVara.springJwt.controller.Reports;

import com.PracticaVara.springJwt.DTOs.ReportDTO;
import com.PracticaVara.springJwt.service.AnnouncementServices.ReportService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "${spring.originUrl}")
@RequestMapping("api/Reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }
    @PostMapping("create-report")
    public ResponseEntity<?> reportAnnouncement(@RequestBody ReportDTO request){
        return reportService.createReport(request);
    }
    @GetMapping("my-reports")
    public ResponseEntity<?> getMyReports(){
        return reportService.findAllReportsByUserOrdered();
    }

    @DeleteMapping("remove-report")
    public ResponseEntity<?> deleteReport(@PathVariable Integer reportId){
        return reportService.deleteReport(reportId);
    }

}
