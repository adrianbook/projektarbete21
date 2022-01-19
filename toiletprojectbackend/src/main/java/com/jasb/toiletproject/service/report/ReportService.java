package com.jasb.toiletproject.service.report;

import com.jasb.entities.Report;

import java.util.List;

public interface ReportService {
    Report report(Report report);
    List<Report> getAllReports();
    List<Report> getAllReportsForNonExistentToilet();
    List<Report> getAllReportsWithUserDefinedIssue();
}
