package com.jasb.toiletproject.rest;

import com.jasb.entities.Rating;
import com.jasb.entities.Toilet;
import com.jasb.entities.ToiletUser;
import com.jasb.toiletproject.exceptions.ToiletNotFoundException;
import com.jasb.toiletproject.exceptions.ToiletUserNotFoundException;
import com.jasb.toiletproject.service.rating.RatingService;
import com.jasb.toiletproject.service.toilet.ToCloseToAnotherToiletException;
import com.jasb.toiletproject.service.toilet.ToiletService;
import lombok.RequiredArgsConstructor;
import com.jasb.toiletproject.repo.ToiletRepository;
import com.jasb.entities.Toilet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.*;


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
            toiletService.addToilet(t);
        } catch (ToCloseToAnotherToiletException e) {
            return new ResponseEntity<>("Toilet to close to another " +
                    "toilet", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(t, HttpStatus.CREATED);
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
    public ResponseEntity setRatingForToilet(@RequestBody RatingRestObject ratingRestObject) {
        try {

            Rating rating;
            Optional<Toilet> fetchedToilet = toiletService.getToiletById(ratingRestObject.toiletId);
            if (fetchedToilet.isEmpty()) throw new ToiletNotFoundException(ratingRestObject.toiletId);

            Toilet toilet = fetchedToilet.get();
            ToiletUser user = ratingService.fetchToiletUser();

            Optional<Rating> fetchedRating = ratingService.checkIfRatingExistForUserAndToilet(user, toilet);

            if (fetchedRating.isEmpty()) {
                rating = ratingService.addRating(
                        new Rating(toilet, user, ratingRestObject.rating, ratingRestObject.notes));
                log.info("added rating: " + rating);
                return new ResponseEntity<Rating>(rating, HttpStatus.CREATED);
            } else {
                rating = fetchedRating.get();
                rating.setRating(ratingRestObject.rating);
                rating.setNotes(ratingRestObject.notes);
                rating = ratingService.addRating(rating);

                log.info("added rating: " + rating);
                return new ResponseEntity<Rating>(rating, HttpStatus.OK);
            }
        } catch (ToiletUserNotFoundException e) {
            log.error("could not find toiletuser " + e.getCause().getMessage());
            return new ResponseEntity("server error. could not find user", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ToiletNotFoundException e) {
            log.error(e.getLocalizedMessage());
            return new ResponseEntity("server error. could not find toilet", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

class RatingRestObject {
    int toiletId;
    int rating;
    String notes;

    public RatingRestObject(int toiletId, int rating, String notes) {
        this.toiletId = toiletId;
        this.rating = rating;
        this.notes = notes;
    }
}


