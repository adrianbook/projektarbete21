package com.jasb.toiletproject.rest;

import com.jasb.entities.Report;
import com.jasb.toiletproject.repo.ToiletRepository;
import com.jasb.entities.Toilet;
import com.jasb.toiletproject.service.rating.RatingService;
import com.jasb.toiletproject.service.report.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * RestController class intended for uses by admin only.
 * Contains endpoints that a basic user should not have
 * access to.
 *
 * Written by JASB
 */
@Slf4j
@RestController
@RequestMapping("admin/api/v1/toilets")
public class AdminToiletRestController {

    /**
     * Dependency injection of JPArepository
     */
    @Autowired
    ToiletRepository data;
    @Autowired
    ReportService reportService;
    @Autowired
    RatingService ratingService;

    /**
     * GET endpoint for getting all the toilets from the admin api
     * @return a list of alla toilets in the database
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ToiletList allToilets() {
        log.info("Returning all toilets");
        return new ToiletList(data.findAll());
    }

    /**
     * POST endpoint for adding a toilet from the admin api
     * @param t JSON request body from the post request
     * @return a json representation of the added toilet and a statuscode.
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity addToilet(@RequestBody Toilet t) {
        data.save(t);
        log.info("Adding new toilet at longitude: {} latitude:  {}", t.getLongitude(), t.getLatitude());
        return new ResponseEntity<Toilet>(t, HttpStatus.CREATED);
    }

    /**
     * GET endpoint for getting a toilet by its assigned id
     * takes an id in the path
     * @param id id from the request uri
     * @return
     */
    @GetMapping(path = "{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Optional<Toilet> getToiletById(@PathVariable("id") long id) {
        log.info("Finding toilet with id {}", id);
        return data.findById(id);
    }

    /**
     * DELETE endpoint to delete a toilet ny given id
     * @param id id from the request uri
     */
    @DeleteMapping(path = "{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void deleteToilet(@PathVariable("id") long id) {
        log.info("Deleting toilet with id {}", id);
        reportService.deleteByToiletId(id);
        ratingService.deleteRatingByToiletId(id);
        data.deleteById(id);
    }

    /**
     * PUT endpoint to update an existing Toilet in the database by given id
     * @param id id from the request uri
     * @param newToilet JSON representation of the new data to be saved in the toilet
     * @return response
     */
    @PutMapping(path = "{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Optional<Toilet> updateToilet(@PathVariable("id") long id, @RequestBody Toilet newToilet) {
        log.info("Updating toilet with id {} with info {}", id, newToilet.toString());
        return data.findById(id)
                .map(toilet -> {
                    toilet.setLongitude(newToilet.getLongitude());
                    toilet.setLatitude(newToilet.getLatitude());
                    return data.save(toilet);
                });
    }

    @GetMapping("reports/getall")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<Report> getAllReports() {
        return reportService.getAllReports();
    }

    @GetMapping("reports/getallnonexistingtoilets")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<Report> getAllReportsForNonExistingToilets() {
        return reportService.getAllReportsForNonExistentToilet();
    }

    @GetMapping("reports/getalluserdefinedreports")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<Report> getAllUserDefinedReports() {
        return reportService.getAllReportsWithUserDefinedIssue();
    }
}
