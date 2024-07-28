package com.tarkhan.blogapp.service.impl;

import com.tarkhan.blogapp.entity.Post;
import com.tarkhan.blogapp.entity.Report;
import com.tarkhan.blogapp.entity.User;
import com.tarkhan.blogapp.exception.ResourceNotFoundException;
import com.tarkhan.blogapp.model.report.CreateReportDto;
import com.tarkhan.blogapp.model.report.ReportDto;
import com.tarkhan.blogapp.repository.PostRepository;
import com.tarkhan.blogapp.repository.ReportRepository;
import com.tarkhan.blogapp.repository.UserRepository;
import com.tarkhan.blogapp.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ReportDto> getAllReports() {
        return reportRepository.findAll().stream()
                .map(report -> modelMapper.map(report, ReportDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ReportDto getReportById(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Report", "ID", id));
        return modelMapper.map(report, ReportDto.class);
    }

    @Override
    public ReportDto createReport(CreateReportDto createReportDto) {
        Report report = modelMapper.map(createReportDto, Report.class);

        User reportedBy = userRepository.findById(createReportDto.getReportedById())
                .orElseThrow(() -> new ResourceNotFoundException("User", "ID", createReportDto.getReportedById()));
        Post post = postRepository.findById(createReportDto.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post", "ID", createReportDto.getPostId()));

        report.setReportedBy(reportedBy);
        report.setPost(post);
        report.setCreatedAt(LocalDateTime.now());

        report = reportRepository.save(report);
        return modelMapper.map(report, ReportDto.class);
    }

    @Override
    public void deleteReport(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Report", "ID", id));
        reportRepository.delete(report);
    }
}
