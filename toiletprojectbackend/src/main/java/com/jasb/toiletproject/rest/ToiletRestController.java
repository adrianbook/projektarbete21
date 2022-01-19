package com.jasb.toiletproject.rest;

import com.jasb.entities.*;
import com.jasb.toiletproject.exceptions.ToiletNotFoundException;
import com.jasb.toiletproject.exceptions.ToiletUserNotFoundException;
import com.jasb.toiletproject.service.rating.RatingService;
import com.jasb.toiletproject.service.report.ReportService;
import com.jasb.toiletproject.service.toilet.ToCloseToAnotherToiletException;
import com.jasb.toiletproject.service.toilet.ToiletService;
import com.jasb.toiletproject.util.ToiletUserFetcher;

import com.jasb.toiletproject.service.toiletuser.ToiletUserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;


/**
 * RestController class intended for uses by users.
 * Contains endpoints that a basic user should have
 * access to. Also contains open endpoint that return
 * all the toilets in the database
 *
 * Written by JASB
 */

@RestController
@RequestMapping("api/v1/toilets")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class ToiletRestController {
    /**
     * Dependencyinjection of Services
     */

    private final ToiletService toiletService;
    private final RatingService ratingService;
    private final ToiletUserService toiletUserService;
    private final ReportService reportService;



    /**
     * Open GET endpoint that returns all the toilets in the database
     *
     * @return list of all the toilets in the database
     */
    @GetMapping("/getalltoilets")
    public ToiletList allToilets() {
        return new ToiletList(toiletService.getAllToilets());
    }


    /**
     * POST endpoint for adding a new toilet. Open to anyone with
     * the ROLE_APPUSER credentiols.
     *
     * @param t a JSON representation of a toilet in the request body
     * @return a JSON representation of the created toilet and a responsecode
     */
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ROLE_APPUSER', 'ROLE_ADMIN')")
    public ResponseEntity addToilet(@RequestBody Toilet t) {
        try {
            Toilet addedToilet = toiletService.addToilet(t);
            return new ResponseEntity<>(addedToilet, HttpStatus.CREATED);
        } catch (ToCloseToAnotherToiletException e) {
            return new ResponseEntity<>("Toilet to close to another " +
                    "toilet", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * GET endpont for getting a toilet by its assigned id Open to anyone with
     * the ROLE_APPUSER credentiols.
     *
     * @param id id from the request URI
     * @return A toilet
     */
    @GetMapping(path = "{id}")
    @PreAuthorize("hasAnyRole('ROLE_APPUSER', 'ROLE_ADMIN')")
    public Optional<Toilet> getToiletById(@PathVariable("id") long id) {
        return toiletService.getToiletById(id);
    }

    @PutMapping("/rate")
    @PreAuthorize("hasAnyRole('ROLE_APPUSER', 'ROLE_ADMIN')")
    public ResponseEntity setRatingForToilet(@RequestBody Rating rating) {
        try {

            Optional<Toilet> fetchedToilet = toiletService.getToiletById(rating.getToiletId());
            if (fetchedToilet.isEmpty()) throw new ToiletNotFoundException(rating.getToiletId());

            Toilet toilet = fetchedToilet.get();

            ToiletUser user = ToiletUserFetcher.fetchToiletUserByContext();

            Optional<Rating> fetchedRating = ratingService.checkIfRatingExistForUserAndToilet(user, toilet);

            if (fetchedRating.isEmpty()) {
                rating.setToilet(toilet);
                rating.setToiletUser(user);
                rating = ratingService.addRating(rating);
                log.info("added rating: " + rating);
                return new ResponseEntity<Rating>(rating, HttpStatus.CREATED);
            } else {
                Rating oldRating = fetchedRating.get();
                oldRating.setRating(rating.getRating());
                oldRating.setNotes(rating.getNotes());
                rating = ratingService.addRating(oldRating);

                log.info("added rating: " + rating);
                return new ResponseEntity<Rating>(rating, HttpStatus.OK);
            }
        } catch (ToiletUserNotFoundException e) {
            log.error("could not find toiletuser " + e.getCause().getMessage());
            return new ResponseEntity("server error. could not find user", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ToiletNotFoundException e) {
            log.error(e.getLocalizedMessage());
            return new ResponseEntity("server error. could not find toilet", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("unexpected error adding rating: ");
            e.printStackTrace();
            return new ResponseEntity("error adding rating", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    /*
    report a toilet. takes a json object containing fields:
        int toiletId
        boolean notAToilet
        string issue
     */
    @PostMapping("/reports/report")
    @PreAuthorize("hasAnyRole('ROLE_APPUSER', 'ROLE_ADMIN')")
    public ResponseEntity reportToilet(@RequestBody Report report) {
        try {
        Optional<Toilet> toiletOptional = toiletService.getToiletById(report.getToiletId());
        if (toiletOptional.isEmpty()) throw new ToiletNotFoundException(report.getToiletId());
        Toilet toilet = toiletOptional.get();
        ToiletUser toiletUser = toiletUserService.fetchToiletUser();

        report.setToilet(toilet);
        report.setOwningUser(toiletUser);

        report = reportService.report(report);

        return new ResponseEntity<Report>(report, HttpStatus.CREATED);
        } catch (ToiletUserNotFoundException e) {
            log.error("could not find toiletuser "+ e.getCause().getMessage());
            return new ResponseEntity("server error. could not find user", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ToiletNotFoundException e) {
            log.error(e.getLocalizedMessage());
            return new ResponseEntity("server error. could not find toilet", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}




