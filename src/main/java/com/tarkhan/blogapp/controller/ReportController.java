package com.tarkhan.blogapp.controller;

import com.tarkhan.blogapp.constants.Constants;
import com.tarkhan.blogapp.model.ResponseModel;
import com.tarkhan.blogapp.model.report.CreateReportDto;
import com.tarkhan.blogapp.model.report.ReportDto;
import com.tarkhan.blogapp.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    @Operation(summary = "Get All Reports")
    public ResponseEntity<List<ReportDto>> getAllReports() {
        List<ReportDto> reports = reportService.getAllReports();
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Report by ID")
    public ResponseEntity<ReportDto> getReportById(@PathVariable Long id) {
        ReportDto report = reportService.getReportById(id);
        return ResponseEntity.ok(report);
    }

    @PostMapping
    @Operation(summary = "Create Report")
    public ResponseEntity<ResponseModel> createReport(@Valid @RequestBody CreateReportDto createReportDto) {
        ReportDto report = reportService.createReport(createReportDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseModel(
                        Constants.STATUS_201,
                        Constants.MESSAGE_201
                ));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Report by ID")
    public ResponseEntity<ResponseModel> deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new ResponseModel(
                        Constants.STATUS_204,
                        Constants.MESSAGE_204
                ));
    }
}
