package com.jasb.toiletproject.service.report;

import com.jasb.entities.Report;
import com.jasb.toiletproject.repo.ReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService{

    @Autowired
    ReportRepository reportRepo;

    @Override
    public Report report(Report report) {
        return reportRepo.report(report);
    }

    @Override
    public List<Report> getAllReports() {
        return reportRepo.findAll().stream().map(r -> removePassWordAndRoles(r)).collect(Collectors.toList());
    }

    @Override
    public List<Report> getAllReportsForNonExistentToilet() {
        return reportRepo.findAllToiletNonExistentReports().stream().map(r -> removePassWordAndRoles(r)).collect(Collectors.toList());
    }

    @Override
    public List<Report> getAllReportsWithUserDefinedIssue() {
        return reportRepo.findAll().stream()
                .filter(report -> report.getIssue().length() != 0).collect(Collectors.toList());
    }

    @Override
    public void deleteByToiletId(long toiletId) {
        reportRepo.deleteAllByToiletId(toiletId);
    }
    private Report removePassWordAndRoles(Report report) {
        report.getOwningUser().setRoles(null);
        report.getOwningUser().setPassword(null);
        return report;
    }
}
