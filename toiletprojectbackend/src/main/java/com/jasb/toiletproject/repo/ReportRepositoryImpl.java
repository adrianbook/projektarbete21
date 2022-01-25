package com.jasb.toiletproject.repo;

import com.jasb.entities.Report;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    public void deleteAllByToiletId(long toiletId) {
        em.createQuery("delete from Report as report where report.toilet.Id=:toiletId")
                .setParameter("toiletId", toiletId)
                .executeUpdate();
    }
}
