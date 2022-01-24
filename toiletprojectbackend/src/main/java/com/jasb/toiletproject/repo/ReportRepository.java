package com.jasb.toiletproject.repo;

import com.jasb.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findAllToiletNonExistentReports();
    Report report(Report report);
    void deleteAllByToiletId(long toiletId);
}
