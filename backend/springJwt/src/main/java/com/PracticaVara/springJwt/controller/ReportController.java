package com.PracticaVara.springJwt.controller;

import com.PracticaVara.springJwt.service.ReportService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/Reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }
    @PostMapping("create-report")
    public ResponseEntity<?> reportAnnouncement(@RequestBody JsonNode request){
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
