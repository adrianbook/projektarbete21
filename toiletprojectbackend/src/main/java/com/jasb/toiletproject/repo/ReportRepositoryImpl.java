package com.jasb.toiletproject.repo;

import com.jasb.entities.Report;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component
@Transactional
public class ReportRepositoryImpl {

    @PersistenceContext
    EntityManager em;

    public Report report(Report report) {
        em.persist(report);
        em.flush();
        em.detach(report);
        report.getOwningUser().setRoles(null);
        report.getOwningUser().setPassword(null);
        return report;
    }

    public List<Report> findAllToiletNonExistentReports() {
        return em.createQuery("select report from Report as report where report.notAToilet = true")
                .getResultList();
    }

    public List<Report> findAllUserDefinedIssueReports() {
        return em.createQuery("select report from Report as report where report.issue is not null")
                .getResultList();
    }
}
