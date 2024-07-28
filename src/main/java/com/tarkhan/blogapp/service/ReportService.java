package com.tarkhan.blogapp.service;

import com.tarkhan.blogapp.model.report.CreateReportDto;
import com.tarkhan.blogapp.model.report.ReportDto;

import java.util.List;

public interface ReportService {
    List<ReportDto> getAllReports();
    ReportDto getReportById(Long id);
    ReportDto createReport(CreateReportDto createReportDto);
    void deleteReport(Long id);
}
