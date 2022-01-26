package com.jasb.toiletproject.repo;

import com.jasb.entities.Report;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
/**
 * An implementation of JPA-Repository ReportRepository with some custom queries
 */
@Component
@Transactional
public class ReportRepositoryImpl {
    /**
     * Injection of the entity manager for this implementation
     */
    @PersistenceContext
    EntityManager em;

    /**
     * Method to persist a report.
     * @param report
     * @return the report with users roles and password removed
     */
    public Report report(Report report) {
        em.persist(report);
        em.flush();
        em.detach(report);
        report.getOwningUser().setRoles(null);
        report.getOwningUser().setPassword(null);
        return report;
    }

    /**
     * Method to return all reports of non-existing toilets
     * @return list of reports
     */
    public List<Report> findAllToiletNonExistentReports() {
        return em.createQuery("select report from Report as report where report.notAToilet = true")
                .getResultList();
    }

    /**
     * A method to delete all reports of a toilet. Is called before deleting a toilet
     * @param toiletId
     */
    public void deleteAllByToiletId(long toiletId) {
        em.createQuery("delete from Report as report where report.toilet.Id=:toiletId")
                .setParameter("toiletId", toiletId)
                .executeUpdate();
    }
}